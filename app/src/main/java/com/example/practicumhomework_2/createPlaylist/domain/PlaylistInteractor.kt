package com.example.practicumhomework_2.createPlaylist.domain

import com.example.practicumhomework_2.createPlaylist.data.PlaylistDao
import com.example.practicumhomework_2.createPlaylist.data.PlaylistRepositoryImpl
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity

class PlaylistInteractor(
    private val repository: PlaylistRepository
) {
    suspend fun addPlaylist(playlist: PlaylistEntity) {
        repository.addPlaylist(playlist)
    }

    suspend fun deletePlaylist(playlist: PlaylistEntity) {
        repository.deletePlaylist(playlist)
    }

    suspend fun addTrackToPlaylist(trackId: String, playlistId: Int) {
        repository.addTrackToPlaylist(trackId, playlistId)
    }

    suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        repository.deleteTrackFromPlaylist(trackId,playlistId)
    }
}