package com.example.practicumhomework_2.search.data.network

import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.player.domain.PlayerState
import com.example.practicumhomework_2.search.domain.SearchState
import com.example.practicumhomework_2.search.domain.TrackSearchWrapper

class TrackSearchWrapperImpl(private val searchApi: TracksSearchApi) : TrackSearchWrapper {

    override suspend fun searchTracks(value: String): SearchState {
         val searchResponse = searchApi.searchTracks(value)
        return if(searchResponse.isSuccessful) {
            SearchState.Success(searchResponse.body()?.results ?: emptyList())
        } else {
            SearchState.Error(searchResponse.message())
        }
    }

    override suspend fun searchSingleTrack(trackId: String): PlayerState {
        searchApi.searchTracks(trackId)
        val searchResponse = searchApi.searchTracks(trackId)
        return if(searchResponse.isSuccessful) {
            searchResponse.body()?.results?.first()?.let { PlayerState.TrackLoaded(it) }
                ?:PlayerState.Error(searchResponse.message())
        } else {
            PlayerState.Error(searchResponse.message())
        }
    }
}