package com.example.practicumhomework_2.player.presentation

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.player.domain.FavouriteTracksInteractor
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.domain.PlayerState
import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val favoriteTracksInteractor: FavouriteTracksInteractor
) : ViewModel() {
    private val _trackLiveData = MutableLiveData<PlayerState>()
    val trackLiveData: LiveData<PlayerState> = _trackLiveData
    private val _isFavoriteLiveData = MutableLiveData<Boolean>()
    val isFavoriteLiveData: LiveData<Boolean> = _isFavoriteLiveData
    private var job: Job? = null
    private val formatter = SimpleDateFormat("mm:ss", Locale.getDefault())
    private lateinit var currentTrack: Track

    fun startProgress(mediaPlayer: MediaPlayer) {
        job?.cancel()
        job = viewModelScope.launch {
            while (isActive) {
                delay(DELAY)
                _trackLiveData.value =
                    PlayerState.InProgress(formatter.format(mediaPlayer.currentPosition))
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            if (!currentTrack.isFavorite) {
                favoriteTracksInteractor.addTrackToFavorite(currentTrack)
            } else {
                favoriteTracksInteractor.deleteTrackFromFavorite(currentTrack)
            }
            currentTrack.isFavorite = !currentTrack.isFavorite
            _isFavoriteLiveData.value = currentTrack.isFavorite
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
            val result = playerInteractor.searchTrack(trackId)
            _trackLiveData.value = result
            if (result is PlayerState.TrackLoaded) {
                currentTrack = result.track
                _isFavoriteLiveData.value = result.track.isFavorite
            }
        }
    }

    companion object {
        private const val DELAY = 300L
    }

}
