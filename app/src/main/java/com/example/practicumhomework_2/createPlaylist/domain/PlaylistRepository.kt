package com.example.practicumhomework_2.createPlaylist.domain

import androidx.lifecycle.LiveData
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    suspend fun addPlaylist(playlist: PlaylistEntity)
    suspend fun deletePlaylist(playlist: PlaylistEntity)
    suspend fun addTrackToPlaylist(trackId: String, playlistId: Int)
    suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int)
    fun getPlaylists(): LiveData<List<PlaylistModel>>
    suspend fun addTrack(track: Track)
    fun getPlaylistById(id: Int): Flow<DetailedPlaylistModel>
}