package com.example.practicumhomework_2.createPlaylist.domain

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.practicumhomework_2.createPlaylist.data.ImageSaver
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.Flow

class PlaylistInteractor(
    private val repository: PlaylistRepository,
    private val imageSaver: ImageSaver
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
    suspend fun addTrack(track: Track, playlistId: Int) {
        repository.addTrack(track)
        repository.addTrackToPlaylist(track.trackId, playlistId)
    }
    fun saveToInternal(uri: Uri, name: String): Uri {
        return imageSaver.saveToInternal(uri, name)
    }
    fun getPlaylistById(id: Int): Flow<DetailedPlaylistModel> {
        return repository.getPlaylistById(id)
    }
}