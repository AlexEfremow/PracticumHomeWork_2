package com.example.practicumhomework_2.createPlaylist.domain

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.media.domain.PlaylistModel

interface PlaylistRepository {
    suspend fun addPlaylist(playlist: PlaylistEntity)
    suspend fun deletePlaylist(playlist: PlaylistEntity)
    suspend fun addTrackToPlaylist(trackId: String, playlistId: Int)
    suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int)
    fun getPlaylists(): LiveData<List<PlaylistModel>>
}