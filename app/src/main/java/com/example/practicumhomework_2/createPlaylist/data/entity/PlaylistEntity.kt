package com.example.practicumhomework_2.createPlaylist.data.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practicumhomework_2.media.domain.PlaylistModel

@Entity
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val cover: String,
    val trackList: String = "",
    val trackCount: Int = 0
) {
    fun mapToUi(): PlaylistModel {
        return PlaylistModel(id = id, cover = cover, name = name, count = trackCount)
    }
}