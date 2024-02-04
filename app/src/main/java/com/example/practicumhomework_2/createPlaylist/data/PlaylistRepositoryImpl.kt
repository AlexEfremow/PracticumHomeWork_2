package com.example.practicumhomework_2.createPlaylist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.practicumhomework_2.addToPlaylist.data.PlaylistTrackDao
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistRepository
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PlaylistRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val playlistTrackDao: PlaylistTrackDao
) : PlaylistRepository {

    override suspend fun addPlaylist(playlist: PlaylistEntity) {
        playlistDao.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: DetailedPlaylistModel) {
        playlistDao.deletePlaylist(playlist.id)
        playlist.trackList.forEach {
            if(!checkTrackUsage(it.trackId)) playlistTrackDao.deleteTrack(it.trackId)
        }
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
        list.remove(trackId)
        playlistDao.updateTrackList(playlistId, list.joinToString(SEPARATOR))
        val isUsed = checkTrackUsage(trackId)
        if (!isUsed) {
            playlistTrackDao.deleteTrack(trackId)
        }
    }
    private suspend fun checkTrackUsage(trackId: String): Boolean {
        val playlists = playlistDao.getPlaylists()
        var isUsed = false
        for (i in playlists) {
            if(i.parsedTrackList.contains(trackId)) {
                isUsed = true
                break
            }
        }
        return isUsed
    }

    override fun getPlaylists(): LiveData<List<PlaylistModel>> {
        return playlistDao.getPlaylistsLiveData().map { list -> list.map { it.mapToUi() } }
    }

    override suspend fun addTrack(track: Track) {
        playlistTrackDao.addTrack(track.toPlaylistDbModel())
    }

    override fun getPlaylistById(id: Int): Flow<DetailedPlaylistModel> {
        val playlistFlow = playlistDao.getPlaylistById(id)
        val tracksFlow = playlistTrackDao.getAllTracks()
        return playlistFlow.combine(tracksFlow) { playlist, trackList ->
            DetailedPlaylistModel(
                playlist.id,
                playlist.cover,
                playlist.name,
                playlist.description,
                playlist.parsedTrackList.size,
                (trackList.sumOf { it.trackTime } / 60000).toInt(),
                trackList.map { it.mapToTrack() }.filter { playlist.parsedTrackList.contains(it.trackId) }
            )
        }
    }

    companion object {
        private const val SEPARATOR = "|"
    }

}