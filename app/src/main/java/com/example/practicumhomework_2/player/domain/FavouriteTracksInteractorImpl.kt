package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteTracksInteractorImpl(private val repository: FavoriteTracksRepository): FavouriteTracksInteractor {
    override suspend fun addTrackToFavorite(track: Track) {
        repository.addTrackToFavorite(track)
    }

    override suspend fun deleteTrackFromFavorite(track: Track) {
        repository.deleteTrackFromFavorite(track)
    }

    override suspend fun getFavoriteTracks(): Flow<List<Track>> {
        return repository.getFavoriteTracks().map { it.reversed() }
    }
}