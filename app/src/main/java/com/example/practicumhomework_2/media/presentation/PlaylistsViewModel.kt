package com.example.practicumhomework_2.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import com.example.practicumhomework_2.media.domain.FavoriteTracksState
import kotlinx.coroutines.launch

class PlaylistsViewModel(private val interactor: PlaylistInteractor): ViewModel() {

    val playlistsLiveData = interactor.getPlaylists()
}