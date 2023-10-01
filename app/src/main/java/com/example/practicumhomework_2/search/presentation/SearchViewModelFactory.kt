package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicumhomework_2.search.data.network.TrackSearchWrapperImpl
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchPreferences

class SearchViewModelFactory(private val searchPreferences: SearchPreferences): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == SearchViewModel::class.java) {
            return SearchViewModel(SearchInteractor(searchPreferences, TrackSearchWrapperImpl(TracksSearchApi.retrofit))) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}