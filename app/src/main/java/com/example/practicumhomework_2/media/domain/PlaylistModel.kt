package com.example.practicumhomework_2.media.domain

data class PlaylistModel(
    val id: Int,
    val cover: String,
    val name: String,
    val count: Int,
    val trackList: List<String>
)