package com.example.practicumhomework_2.search.data.network

import com.example.practicumhomework_2.search.domain.TrackSearchWrapper
import retrofit2.Call

class TrackSearchWrapperImpl(private val searchApi: TracksSearchApi): TrackSearchWrapper {
    override fun searchTracks(value: String): Call<TrackSearchResponse> {
        return searchApi.searchTracks(value)
    }
}