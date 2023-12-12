package com.example.practicumhomework_2.player.presentation

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
    private var counter = 0
    private var job: Job? = null

    fun startProgress() {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                delay(DELAY)
                counter += 1000
                _stateFlow.value = PlayerState.InProgress(counter)
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
        private const val DELAY = 1000L
    }

}
