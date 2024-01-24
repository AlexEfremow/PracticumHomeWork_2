package com.example.practicumhomework_2.player.domain.entity

import android.os.Parcelable
import com.example.practicumhomework_2.addToPlaylist.data.entity.PlaylistTrackDbEntity
import com.example.practicumhomework_2.data.db.entity.FavoriteTrackDbEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
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
) : Parcelable {
    fun getCoverArtwork() = artworkUrl.replaceAfterLast('/', "512x512bb.jpg")
    fun timeFormat(): String = SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime)

    fun toFavoriteDbModel() = FavoriteTrackDbEntity(
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

    fun toPlaylistDbModel() = PlaylistTrackDbEntity(
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