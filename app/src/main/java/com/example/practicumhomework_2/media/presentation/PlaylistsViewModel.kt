package com.example.practicumhomework_2.media.presentation

import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor

class PlaylistsViewModel(private val interactor: PlaylistInteractor): ViewModel() {

    val playlistsLiveData = interactor.getPlaylists()
}