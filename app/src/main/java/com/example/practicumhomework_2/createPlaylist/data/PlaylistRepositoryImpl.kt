package com.example.practicumhomework_2.createPlaylist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistRepository
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlaylistRepositoryImpl(private val dao: PlaylistDao, private val gson: Gson) : PlaylistRepository {

    private val typeToken = object : TypeToken<ArrayList<Track>>() {}.type

    override suspend fun addPlaylist(playlist: PlaylistEntity) {
        dao.addPlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: PlaylistEntity) {
        dao.deletePlaylist(playlist)
    }

    override suspend fun addTrackToPlaylist(trackId: String, playlistId: Int) {
        val json = dao.getTrackListForPlaylist(playlistId)
        val list = gson.fromJson<List<String>>(json, typeToken)
        list.toMutableList().add(trackId)
        dao.updateTrackList(playlistId, gson.toJson(list))
    }

    override suspend fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        val json = dao.getTrackListForPlaylist(playlistId)
        val list = gson.fromJson<List<String>>(json, typeToken)
        list.toMutableList().remove(trackId)
        dao.updateTrackList(playlistId, gson.toJson(list))
    }

    override fun getPlaylists(): LiveData<List<PlaylistModel>> {
        return dao.getPlaylists().map { list -> list.map { it.mapToUi() } }
    }
}