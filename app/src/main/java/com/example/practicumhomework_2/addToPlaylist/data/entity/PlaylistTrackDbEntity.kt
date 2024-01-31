package com.example.practicumhomework_2.addToPlaylist.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practicumhomework_2.player.domain.entity.Track

@Entity
data class PlaylistTrackDbEntity(
    val trackName: String,
    val artistName: String,
    val trackTime: Long,
    val artworkUrl: String,
    @PrimaryKey
    val trackId: String,
    val releaseDate: String,
    val country: String,
    val primaryGenreName: String,
    val collectionName: String,
    val previewUrl: String,
    var isFavorite: Boolean = false
) {
    fun mapToTrack(): Track {
        return Track(
            trackName,
            artistName,
            trackTime,
            artworkUrl,
            trackId,
            releaseDate,
            country,
            primaryGenreName,
            collectionName,
            previewUrl
        )
    }
}