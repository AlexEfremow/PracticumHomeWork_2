package com.example.practicumhomework_2.addToPlaylist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.practicumhomework_2.addToPlaylist.data.entity.PlaylistTrackDbEntity

@Dao
interface PlaylistTrackDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrack(track: PlaylistTrackDbEntity)
}