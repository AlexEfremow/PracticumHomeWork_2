package com.example.practicumhomework_2.playlist.presentation

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.playlist.presentation.model.DetailedPlaylistModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PlaylistViewModel(private val interactor: PlaylistInteractor): ViewModel() {

    var playlistFlow: Flow<DetailedPlaylistModel>? = null

    private val _removingState = MutableStateFlow(false)
    val removingState = _removingState.asStateFlow()

    fun getPlaylistById(id: Int) {
        playlistFlow = interactor.getPlaylistById(id)
    }
    fun deleteTrackFromPlaylist(trackId: String, playlistId: Int) {
        viewModelScope.launch {
            interactor.deleteTrackFromPlaylist(trackId, playlistId)
        }
    }
    fun deletePlaylist(playlist: DetailedPlaylistModel) {
        viewModelScope.launch {
            val removingResult = interactor.deletePlaylist(playlist)
            _removingState.value = removingResult
        }
    }
    fun getShareIntent(trackList: List<Track>, type: String): Intent {
        val text =  buildString {
            append("${trackList.size} треков")
            append("\n")
            trackList.forEachIndexed { index, track ->
                append("${index + 1}. ${track.artistName} - ${track.trackName} (${track.timeFormat()})")
                append("\n")
            }
        }
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            this.type = type
        }

        return Intent.createChooser(sendIntent, null)
    }
}