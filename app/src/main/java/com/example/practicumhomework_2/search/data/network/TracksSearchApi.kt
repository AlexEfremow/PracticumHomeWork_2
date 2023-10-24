package com.example.practicumhomework_2.search.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksSearchApi {

    @GET("search?entity=song")
    fun searchTracks(@Query("term") value: String): Call<TrackSearchResponse>
}