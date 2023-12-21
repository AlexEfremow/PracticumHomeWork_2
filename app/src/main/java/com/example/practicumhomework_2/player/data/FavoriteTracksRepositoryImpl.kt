package com.example.practicumhomework_2.player.data

import com.example.practicumhomework_2.data.db.AppDataBase
import com.example.practicumhomework_2.player.domain.FavoriteTracksRepository
import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class FavoriteTracksRepositoryImpl(private val dataBase: AppDataBase): FavoriteTracksRepository {
    private val trackDao = dataBase.trackDao()
    override suspend fun addTrackToFavorite(track: Track) {
        trackDao.addTrackToFavorites(track.toDbModel())
    }

    override suspend fun deleteTrackFromFavorite(track: Track) {
        trackDao.deleteTrackFromFavorites(track.toDbModel())
    }

    override suspend fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getTracks().map { it.map { it.toDomainModel() }}
    }
}