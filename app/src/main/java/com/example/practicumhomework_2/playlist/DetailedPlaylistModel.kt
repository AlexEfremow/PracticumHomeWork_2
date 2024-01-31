package com.example.practicumhomework_2.playlist

import com.example.practicumhomework_2.player.domain.entity.Track

data class DetailedPlaylistModel(
    val id: Int,
    val cover: String,
    val name: String,
    val description: String,
    val count: Int,
    val totalTime: String,
    val trackList: List<Track>
)