package com.example.practicumhomework_2.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.domain.PlayerState
import kotlinx.coroutines.launch

class PlayerViewModel(private val interactor: PlayerInteractor) : ViewModel() {

    private val _trackLiveData = MutableLiveData<PlayerState>()
    val liveData: LiveData<PlayerState> = _trackLiveData

    fun searchTrack(trackId: String) {
        viewModelScope.launch {
            _trackLiveData.value = interactor.searchTrack(trackId)
        }
    }

}