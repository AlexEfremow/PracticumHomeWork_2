package com.example.practicumhomework_2.createPlaylist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.practicumhomework_2.addToPlaylist.data.PlaylistTrackDao
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistRepository
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlaylistRepositoryImpl(private val dao: PlaylistDao, private val playlistTrackDao: PlaylistTrackDao) : PlaylistRepository {


    override suspend fun addPlaylist(playlist: PlaylistEntity) {
        dao.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: PlaylistEntity) {
        dao.deletePlaylist(playlist)
    }

    override suspend fun addTrackToPlaylist(trackId: String, playlistId: Int) {
        val json = dao.getTrackListForPlaylist(playlistId)
        val list = json.split(SEPARATOR).filter { it.isNotBlank() }.toMutableList()
        list.add(trackId)
        dao.updateTrackList(playlistId, list.joinToString(SEPARATOR))
    }

    override suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        val json = dao.getTrackListForPlaylist(playlistId)
        val list = json.split(SEPARATOR).toMutableList()
        val index = list.indexOf(trackId)
        list.removeAt(index-1)
        list.remove(trackId)
        dao.updateTrackList(playlistId, list.joinToString(SEPARATOR))
    }

    override fun getPlaylists(): LiveData<List<PlaylistModel>> {
        return dao.getPlaylists().map { list -> list.map { it.mapToUi() } }
    }

    override suspend fun addTrack(track: Track) {
        playlistTrackDao.addTrack(track.toPlaylistDbModel())
    }

    companion object{
        private const val SEPARATOR = "|"
    }

}