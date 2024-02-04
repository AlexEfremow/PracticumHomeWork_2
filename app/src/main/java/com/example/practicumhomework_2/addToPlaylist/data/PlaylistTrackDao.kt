package com.example.practicumhomework_2.addToPlaylist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicumhomework_2.addToPlaylist.data.entity.PlaylistTrackDbEntity
import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistTrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrack(track: PlaylistTrackDbEntity)
    @Query("SELECT * FROM PlaylistTrackDbEntity")
    fun getAllTracks(): Flow<List<PlaylistTrackDbEntity>>
    @Query("DELETE FROM PlaylistTrackDbEntity WHERE trackId = :trackId")
    suspend fun deleteTrack(trackId: String)
}