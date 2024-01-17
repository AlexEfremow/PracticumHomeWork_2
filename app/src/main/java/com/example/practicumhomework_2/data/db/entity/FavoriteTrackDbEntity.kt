package com.example.practicumhomework_2.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.practicumhomework_2.player.domain.entity.Track

@Entity(tableName = "track_table")
data class FavoriteTrackDbEntity(
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
){
    fun toDomainModel() = Track(trackName, artistName, trackTime, artworkUrl, trackId, releaseDate, country, primaryGenreName, collectionName, previewUrl, true)
}