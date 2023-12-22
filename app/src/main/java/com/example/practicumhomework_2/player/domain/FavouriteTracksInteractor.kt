package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow

interface FavouriteTracksInteractor {
    suspend fun addTrackToFavorite(track: Track)

    suspend fun deleteTrackFromFavorite(track: Track)

    suspend fun getFavoriteTracks(): Flow<List<Track>>
}