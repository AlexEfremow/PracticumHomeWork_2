package com.example.practicumhomework_2.player.domain.entity

import com.example.practicumhomework_2.data.db.entity.TrackDbEntity
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class Track(
    val trackName: String,
    val artistName: String,
    @SerializedName("trackTimeMillis") val trackTime: Long,
    @SerializedName("artworkUrl100") val artworkUrl: String,
    val trackId: String,
    val releaseDate: String,
    val country: String,
    val primaryGenreName: String,
    val collectionName: String,
    val previewUrl: String,
    var isFavorite: Boolean = false
) {
    fun getCoverArtwork() = artworkUrl.replaceAfterLast('/', "512x512bb.jpg")
    fun timeFormat(): String = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime)

    fun toDbModel() = TrackDbEntity(
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