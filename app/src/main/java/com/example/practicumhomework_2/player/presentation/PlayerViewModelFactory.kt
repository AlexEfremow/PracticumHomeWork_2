package com.example.practicumhomework_2.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicumhomework_2.search.data.network.TracksSearchApi

class PlayerViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == PlayerViewModel::class.java) {
            return PlayerViewModel(TracksSearchApi.retrofit) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}