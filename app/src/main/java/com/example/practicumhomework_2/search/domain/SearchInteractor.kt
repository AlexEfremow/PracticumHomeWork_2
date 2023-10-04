package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.entity.Track

class SearchInteractor(
    private val searchPreferences: SearchPreferences,
    private val searchWrapper: TrackSearchWrapper
) {
    fun searchTracks(query: String, callback: TrackListSearchCallBack) {
        searchWrapper.searchTracks(query, callback)
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