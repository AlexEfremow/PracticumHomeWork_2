package com.example.practicumhomework_2.createPlaylist.data.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val cover: String,
    val trackList: String = "",
    val trackCount: Int = 0
)