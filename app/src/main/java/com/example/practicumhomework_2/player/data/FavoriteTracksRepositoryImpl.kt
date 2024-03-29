package com.example.practicumhomework_2.player.data

import com.example.practicumhomework_2.data.db.AppDataBase
import com.example.practicumhomework_2.player.domain.FavoriteTracksRepository
import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteTracksRepositoryImpl(private val dataBase: AppDataBase): FavoriteTracksRepository {
    private val trackDao = dataBase.favoriteTracksDao()
    override suspend fun addTrackToFavorite(track: Track) {
        trackDao.addTrackToFavorites(track.toFavoriteDbModel())
    }

    override suspend fun deleteTrackFromFavorite(track: Track) {
        trackDao.deleteTrackFromFavorites(track.toFavoriteDbModel())
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getTracks().map { it.map { it.toDomainModel() }}
    }
}