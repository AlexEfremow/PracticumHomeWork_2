package com.example.practicumhomework_2

import com.google.gson.annotations.SerializedName
import java.util.*

data class Track(
    val trackName: String,
    val artistName: String,
    @SerializedName("trackTimeMillis") val trackTime: Long,
    @SerializedName("artworkUrl100") val artworkUrl: String,
    val trackId: Int
)

/**
 *
 * {
 * "trackName":"Smells likeTeen Spirit",
 * "artistName":"Nirvana",
 *
 *
 */
