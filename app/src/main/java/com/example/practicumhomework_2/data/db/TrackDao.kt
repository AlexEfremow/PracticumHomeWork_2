package com.example.practicumhomework_2.data.db

import androidx.room.*
import com.example.practicumhomework_2.data.db.entity.TrackDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrackToFavorites(track: TrackDbEntity)

    @Delete
    suspend fun deleteTrackFromFavorites(track: TrackDbEntity)

    @Query("SELECT * FROM track_table")
    fun getTracks(): Flow<List<TrackDbEntity>>

    @Query("SELECT trackId FROM track_table")
    suspend fun getFavoriteTracksIds(): List<String>
}