package com.example.practicumhomework_2.addToPlaylist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.practicumhomework_2.addToPlaylist.data.entity.PlaylistTrackDbEntity
import com.example.practicumhomework_2.player.domain.entity.Track

@Dao
interface PlaylistTrackDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrack(track: PlaylistTrackDbEntity)
    @Query("SELECT * FROM PlaylistTrackDbEntity")
    suspend fun getAllTracks(): List<PlaylistTrackDbEntity>
}