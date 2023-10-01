package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.search.data.network.TrackSearchResponse
import retrofit2.Call

interface TrackSearchWrapper {
    fun searchTracks(value: String): Call<TrackSearchResponse>
}