package com.example.practicumhomework_2.addToPlaylist

import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor

class AddToPlaylistViewModel(private val interactor: PlaylistInteractor): ViewModel() {
    val playlistsLiveData = interactor.getPlaylists()
}