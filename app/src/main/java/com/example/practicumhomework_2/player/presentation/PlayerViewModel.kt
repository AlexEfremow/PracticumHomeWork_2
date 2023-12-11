package com.example.practicumhomework_2.player.presentation

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
    val liveData: LiveData<PlayerState> = _trackLiveData
    private var counter = 0
    private var job: Job? = null

    fun startProgress() {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                delay(DELAY)
                counter += 1000
                _trackLiveData.value = PlayerState.InProgress(counter)
            }
        }
    }
    fun stopProgress() {
        job?.cancel()
    }
    fun resetCounter() {
        _trackLiveData.value = PlayerState.InProgress(0)
    }

    fun searchTrack(trackId: String) {
        viewModelScope.launch {
            _trackLiveData.value = interactor.searchTrack(trackId)
        }
    }

    companion object {
        private const val DELAY = 1000L
    }

}