package com.example.practicumhomework_2.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksSearchApi {

    @GET("search?entity=song")
    fun searchTracks(@Query("term") trackName: String): Call<TrackSearchResponse>

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<TracksSearchApi>()
    }
}