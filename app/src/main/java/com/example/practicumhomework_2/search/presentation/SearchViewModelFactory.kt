package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicumhomework_2.search.data.local.SearchPreferences
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.settings.presentation.SettingsViewModel

class SearchViewModelFactory(private val searchPreferences: SearchPreferences): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == SearchViewModel::class.java) {
            return SearchViewModel(searchPreferences, TracksSearchApi.retrofit) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}