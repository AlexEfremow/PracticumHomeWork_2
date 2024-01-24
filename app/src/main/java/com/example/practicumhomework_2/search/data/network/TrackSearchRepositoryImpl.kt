package com.example.practicumhomework_2.search.data.network

import com.example.practicumhomework_2.data.db.AppDataBase
import com.example.practicumhomework_2.player.domain.PlayerState
import com.example.practicumhomework_2.search.domain.SearchState
import com.example.practicumhomework_2.search.domain.TrackSearchRepository

class TrackSearchRepositoryImpl(
    private val searchApi: TracksSearchApi,
    private val dataBase: AppDataBase
) : TrackSearchRepository {

    override suspend fun searchTracks(value: String): SearchState {
        return try {
            val searchResponse = searchApi.searchTracks(value)
            if (searchResponse.isSuccessful) {
                SearchState.Success(searchResponse.body()?.results ?: emptyList())
            } else {
                SearchState.Error(searchResponse.message())
            }
        } catch (e: Exception) {
            SearchState.Error(e.message ?: "")
        }

    }

    override suspend fun searchSingleTrack(trackId: String): PlayerState {
        try {
            searchApi.searchTracks(trackId)
            val searchResponse = searchApi.searchTracks(trackId)
            return if (searchResponse.isSuccessful) {
                val favoriteTracksIds = dataBase.favoriteTracksDao().getFavoriteTracksIds()
                val track = searchResponse.body()?.results?.first()

                if (favoriteTracksIds.contains(trackId)) {
                    track?.isFavorite = true
                }
                track?.let { PlayerState.TrackLoaded(it) }
                    ?: PlayerState.Error(searchResponse.message())
            } else {
                PlayerState.Error(searchResponse.message())
            }
        } catch (e: Exception) {
            return PlayerState.Error(e.message ?: "")
        }

    }
}