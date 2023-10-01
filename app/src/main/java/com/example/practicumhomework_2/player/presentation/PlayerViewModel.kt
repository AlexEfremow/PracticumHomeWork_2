package com.example.practicumhomework_2.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.domain.PlayerState
import com.example.practicumhomework_2.search.data.network.TrackSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerViewModel(private val interactor: PlayerInteractor) : ViewModel() {

    private val _trackLiveData = MutableLiveData<PlayerState>()
    val liveData: LiveData<PlayerState> = _trackLiveData

    fun searchTrack(trackId: String) {
        val callBack = object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                _trackLiveData.value =
                    PlayerState.TrackLoaded(response.body()?.results?.first() ?: return)
            }

            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                _trackLiveData.value = PlayerState.Error(t.message ?: "")

            }
        }
        interactor.searchTrack(trackId, callBack)
    }
}