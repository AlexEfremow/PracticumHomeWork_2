package com.example.practicumhomework_2.search.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksSearchApi {

    @GET("search?entity=song")
    suspend fun searchTracks(@Query("term") value: String): Response<TrackSearchResponse>
}