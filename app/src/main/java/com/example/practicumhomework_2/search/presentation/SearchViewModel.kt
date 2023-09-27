package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.search.data.local.SearchPreferences
import com.example.practicumhomework_2.search.data.network.TrackSearchResponse
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import retrofit2.Callback

class SearchViewModel(private val searchPreferences: SearchPreferences, private val tracksSearchApi: TracksSearchApi): ViewModel() {
    private val _searchLiveData = MutableLiveData<List<Track>>()
    val searchLiveData : LiveData<List<Track>> = _searchLiveData

    fun getTrackList(query: String, callback: Callback<TrackSearchResponse>) {
        tracksSearchApi.searchTracks(query).enqueue(callback)
    }
}