package com.example.practicumhomework_2.data.db

import androidx.room.*
import com.example.practicumhomework_2.data.db.entity.FavoriteTrackDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrackToFavorites(track: FavoriteTrackDbEntity)

    @Delete
    suspend fun deleteTrackFromFavorites(track: FavoriteTrackDbEntity)

    @Query("SELECT * FROM track_table")
    fun getTracks(): Flow<List<FavoriteTrackDbEntity>>

    @Query("SELECT trackId FROM track_table")
    suspend fun getFavoriteTracksIds(): List<String>
}