package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.search.data.network.TrackSearchResponse
import retrofit2.Callback

class SearchInteractor(
    private val searchPreferences: SearchPreferences,
    private val searchWrapper: TrackSearchWrapper
) {
    fun searchTracks(query: String, callback: Callback<TrackSearchResponse>) {
        searchWrapper.searchTracks(query).enqueue(callback)
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