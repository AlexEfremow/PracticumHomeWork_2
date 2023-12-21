package com.example.practicumhomework_2.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "track_table")
data class TrackDbEntity(
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
)