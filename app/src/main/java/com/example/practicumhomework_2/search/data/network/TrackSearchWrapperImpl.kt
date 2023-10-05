package com.example.practicumhomework_2.search.data.network

import com.example.practicumhomework_2.player.domain.SingleTrackSearchCallBack
import com.example.practicumhomework_2.search.domain.TrackListSearchCallBack
import com.example.practicumhomework_2.search.domain.TrackSearchWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackSearchWrapperImpl(private val searchApi: TracksSearchApi) : TrackSearchWrapper {
    override fun searchTracks(value: String, callback: TrackListSearchCallBack) {
        return searchApi.searchTracks(value).enqueue(object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                callback.onSuccess(response.body()?.results ?: emptyList())
            }

            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                callback.onError(t.message ?: "error")
            }
        })
    }

    override fun searchSingleTrack(trackId: String, callBack: SingleTrackSearchCallBack) {
        searchApi.searchTracks(trackId).enqueue(object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                val result = response.body()
                if (result != null)
                    callBack.onSuccess(result.results.first())
                else {
                    callBack.onError("Something went wrong")
                }
            }

            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                callBack.onError(t.message ?: "error")
            }

        })
    }
}