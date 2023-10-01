package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.search.data.network.TrackSearchResponse
import com.example.practicumhomework_2.search.domain.TrackSearchWrapper
import retrofit2.Callback

class PlayerInteractor(private val searchWrapper: TrackSearchWrapper) {

    fun searchTrack(trackId: String, callback: Callback<TrackSearchResponse>) {
        searchWrapper.searchTracks(trackId).enqueue(callback)
    }
}