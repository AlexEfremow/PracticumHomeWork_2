package com.example.practicumhomework_2.createPlaylist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.practicumhomework_2.addToPlaylist.data.PlaylistTrackDao
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistRepository
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.playlist.DetailedPlaylistModel

class PlaylistRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistTrackDao: PlaylistTrackDao
) : PlaylistRepository {

    override suspend fun addPlaylist(playlist: PlaylistEntity) {
        playlistDao.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: PlaylistEntity) {
        playlistDao.deletePlaylist(playlist)
    }

    override suspend fun addTrackToPlaylist(trackId: String, playlistId: Int) {
        val json = playlistDao.getTrackListForPlaylist(playlistId)
        val list = json.split(SEPARATOR).filter { it.isNotBlank() }.toMutableList()
        list.add(trackId)
        playlistDao.updateTrackList(playlistId, list.joinToString(SEPARATOR))
    }

    override suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        val json = playlistDao.getTrackListForPlaylist(playlistId)
        val list = json.split(SEPARATOR).toMutableList()
        val index = list.indexOf(trackId)
        list.removeAt(index - 1)
        list.remove(trackId)
        playlistDao.updateTrackList(playlistId, list.joinToString(SEPARATOR))
    }

    override fun getPlaylists(): LiveData<List<PlaylistModel>> {
        return playlistDao.getPlaylists().map { list -> list.map { it.mapToUi() } }
    }

    override suspend fun addTrack(track: Track) {
        playlistTrackDao.addTrack(track.toPlaylistDbModel())
    }

    override suspend fun getPlaylistById(id: Int): DetailedPlaylistModel {
        val playlist = playlistDao.getPlaylistById(id)
        val tracks = playlistTrackDao.getAllTracks().filter {
            playlist.parsedTrackList.contains(it.trackId)
        }
        return DetailedPlaylistModel(
            playlist.id,
            playlist.cover,
            playlist.name,
            playlist.description,
            playlist.parsedTrackList.size,
            (tracks.sumOf { it.trackTime } / 60000).toInt(),
            tracks.map { it.mapToTrack() }
        )
    }

    companion object {
        private const val SEPARATOR = "|"
    }

}