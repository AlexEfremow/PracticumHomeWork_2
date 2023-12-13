package com.example.practicumhomework_2.player.presentation

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.domain.PlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlayerViewModel(private val interactor: PlayerInteractor) : ViewModel() {
    private val _stateFlow = MutableStateFlow<PlayerState>(PlayerState.Initial)
    val stateFlow: StateFlow<PlayerState> = _stateFlow.asStateFlow()
    private var job: Job? = null

    fun startProgress(mediaPlayer: MediaPlayer) {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                delay(DELAY)
                _stateFlow.value = PlayerState.InProgress(mediaPlayer.currentPosition)
            }
        }
    }
    fun stopProgress() {
        job?.cancel()
    }
    fun resetCounter() {
        _stateFlow.value = PlayerState.InProgress(0)
    }

    fun searchTrack(trackId: String) {
        viewModelScope.launch {
            _stateFlow.value = interactor.searchTrack(trackId)
        }
    }

    companion object {
        private const val DELAY = 300L
    }

}
