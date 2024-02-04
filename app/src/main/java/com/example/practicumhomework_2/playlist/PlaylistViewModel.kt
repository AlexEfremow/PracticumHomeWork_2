package com.example.practicumhomework_2.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlaylistViewModel(private val interactor: PlaylistInteractor): ViewModel() {

    var playlistFlow: Flow<DetailedPlaylistModel>? = null

    fun getPlaylistById(id: Int) {
        playlistFlow = interactor.getPlaylistById(id)
    }
    fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        viewModelScope.launch {
            interactor.deleteTrackFromPlaylist(trackId, playlistId)
        }
    }
}