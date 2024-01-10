package com.example.practicumhomework_2.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.media.domain.FavoriteTracksState
import com.example.practicumhomework_2.player.domain.FavouriteTracksInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavouriteTracksViewModel(
    private val interactor: FavouriteTracksInteractor
) : ViewModel() {
    private val _favoriteTracksLiveData = MutableLiveData<FavoriteTracksState>()
    val favoriteTracksLiveData: LiveData<FavoriteTracksState> = _favoriteTracksLiveData


    init {
        viewModelScope.launch {
            interactor.getFavoriteTracks().collect {
                if(it.isEmpty()) {
                    _favoriteTracksLiveData.value = FavoriteTracksState.Empty
                } else {
                    _favoriteTracksLiveData.value = FavoriteTracksState.NotEmpty(it)
                }
            }
        }
    }
}