package com.example.practicumhomework_2.addToPlaylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.launch

class AddToPlaylistViewModel(private val interactor: PlaylistInteractor): ViewModel() {
    val playlistsLiveData = interactor.getPlaylists()
    fun addTrackToPlaylist(track: Track) {
        viewModelScope.launch {
            interactor.addTrack(track)
        }
    }
}