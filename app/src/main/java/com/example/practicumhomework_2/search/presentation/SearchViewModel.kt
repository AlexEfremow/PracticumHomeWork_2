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

    fun loadTrackList(query: String, callback: Callback<TrackSearchResponse>) {
        tracksSearchApi.searchTracks(query).enqueue(callback)
    }
    fun saveTrackToHistory(track: Track) {
        searchPreferences.save(track)
    }
    fun getTrackHistory(): List<Track> {
        return searchPreferences.getTrackList().reversed()
    }
    fun clearHistory() {
        searchPreferences.clearHistory()
    }
}