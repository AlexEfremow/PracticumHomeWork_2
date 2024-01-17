package com.example.practicumhomework_2.createPlaylist.domain

import androidx.lifecycle.LiveData
import com.example.practicumhomework_2.createPlaylist.data.PlaylistDao
import com.example.practicumhomework_2.createPlaylist.data.PlaylistRepositoryImpl
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.player.domain.entity.Track

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
    fun getPlaylists(): LiveData<List<PlaylistModel>> {
        return repository.getPlaylists()
    }
    suspend fun addTrack(track: Track) {
        repository.addTrack(track)
    }
}