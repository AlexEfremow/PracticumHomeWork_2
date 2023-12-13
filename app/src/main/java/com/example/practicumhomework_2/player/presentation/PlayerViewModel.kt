package com.example.practicumhomework_2.player.presentation

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.domain.PlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PlayerViewModel(private val interactor: PlayerInteractor) : ViewModel() {
    private val _trackLiveData = MutableLiveData<PlayerState>()
    val trackLiveData: LiveData<PlayerState> = _trackLiveData
    private var job: Job? = null
    private val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())

    fun startProgress(mediaPlayer: MediaPlayer) {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                delay(DELAY)
                _trackLiveData.value = PlayerState.InProgress(formatter.format(mediaPlayer.currentPosition))
            }
        }
    }
    fun stopProgress() {
        job?.cancel()
    }
    fun resetCounter() {
        _trackLiveData.value = PlayerState.InProgress(formatter.format(0))
    }

    fun searchTrack(trackId: String) {
        viewModelScope.launch {
            _trackLiveData.value = interactor.searchTrack(trackId)
        }
    }

    companion object {
        private const val DELAY = 300L
    }

}
