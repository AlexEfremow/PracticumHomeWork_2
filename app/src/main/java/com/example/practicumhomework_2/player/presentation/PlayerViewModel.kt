package com.example.practicumhomework_2.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.player.domain.SingleTrackSearchCallBack
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.domain.PlayerState
import com.example.practicumhomework_2.player.domain.entity.Track

class PlayerViewModel(private val interactor: PlayerInteractor) : ViewModel() {

    private val _trackLiveData = MutableLiveData<PlayerState>()
    val liveData: LiveData<PlayerState> = _trackLiveData

    fun searchTrack(trackId: String) {
        val callBack = object : SingleTrackSearchCallBack {

            override fun onSuccess(data: Track) {
                _trackLiveData.value = PlayerState.TrackLoaded(data)
            }

            override fun onError(message: String) {
                _trackLiveData.value = PlayerState.Error(message)
            }
        }
        interactor.searchTrack(trackId, callBack)
    }
}