package com.example.practicumhomework_2.playlist.presentation.model

import android.os.Parcelable
import com.example.practicumhomework_2.player.domain.entity.Track
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailedPlaylistModel(
    val id: Int,
    val cover: String,
    val name: String,
    val description: String,
    val count: Int,
    val totalTime: Int,
    val trackList: List<Track>
): Parcelable