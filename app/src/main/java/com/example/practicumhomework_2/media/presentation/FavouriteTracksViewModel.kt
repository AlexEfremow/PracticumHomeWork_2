package com.example.practicumhomework_2.media.presentation

import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.player.domain.FavouriteTracksInteractor

class FavouriteTracksViewModel(
    private val interactor: FavouriteTracksInteractor
) : ViewModel() {

    fun getFavoriteTracks() {
        interactor.getFavoriteTracks()
    }
}