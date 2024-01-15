package com.example.practicumhomework_2.createPlaylist.data

import androidx.room.*
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity

@Dao
interface PlaylistDao {
    @Insert
    suspend fun addPlaylist(playlist: PlaylistEntity)
    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity)

    @Query("SELECT trackList FROM PlaylistEntity WHERE id = :playlistId")
    suspend fun getTrackListForPlaylist(playlistId: Int):String

    @Query("UPDATE PlaylistEntity SET trackList = :trackList WHERE id = :playlistId")
    suspend fun updateTrackList(playlistId: Int, trackList:String)
}