package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.entity.Track

class SearchInteractor(
    private val searchPreferences: SearchPreferences,
    private val searchWrapper: TrackSearchWrapper
) {
    suspend fun searchTracks(query: String): SearchState {
        return searchWrapper.searchTracks(query)
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