package com.example.practicumhomework_2.addToPlaylist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.launch

class AddToPlaylistViewModel(private val interactor: PlaylistInteractor) : ViewModel() {
    val playlistsLiveData = interactor.getPlaylists()
    private val _trackAddingResultLiveData = MutableLiveData<PlaylistState>()
    val trackAddingResultLiveData: LiveData<PlaylistState> get() = _trackAddingResultLiveData

    fun addTrackToPlaylist(track: Track, playlistId: Int) {
        viewModelScope.launch {
            val currentPlaylist = playlistsLiveData.value?.find { it.id == playlistId }!!
            if (currentPlaylist.trackList.contains(track.trackId))
                _trackAddingResultLiveData.value = PlaylistState.Unavailable(currentPlaylist.name)
            else {
                interactor.addTrack(track, playlistId)
                _trackAddingResultLiveData.value = PlaylistState.Success(currentPlaylist.name)
            }
        }
    }
}