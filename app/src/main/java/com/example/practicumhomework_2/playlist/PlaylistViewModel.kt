package com.example.practicumhomework_2.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import kotlinx.coroutines.launch

class PlaylistViewModel(private val interactor: PlaylistInteractor): ViewModel() {
    private val _playlistLiveData = MutableLiveData<DetailedPlaylistModel>()
    val playlistLiveData : LiveData<DetailedPlaylistModel> = _playlistLiveData

    fun getPlaylistById(id: Int) {
        viewModelScope.launch {
            _playlistLiveData.value = interactor.getPlaylistById(id)
        }
    }
}