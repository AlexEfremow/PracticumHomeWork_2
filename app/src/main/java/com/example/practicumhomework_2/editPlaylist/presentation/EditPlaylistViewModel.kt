package com.example.practicumhomework_2.editPlaylist.presentation

import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import com.example.practicumhomework_2.createPlaylist.presentation.PlaylistCreateViewModel
import kotlinx.coroutines.launch

class EditPlaylistViewModel(private val interactor: PlaylistInteractor): PlaylistCreateViewModel(interactor) {

    fun saveUpdates(entity: PlaylistEntity){
        viewModelScope.launch {
            interactor.addPlaylist(entity)
        }
    }
}