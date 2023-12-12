package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.player.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.*

sealed class PlayerState {
    class Error(val message: String): PlayerState()
    class TrackLoaded(val track: Track): PlayerState()
    object Initial : PlayerState()
    class InProgress(private val counter: Int) : PlayerState() {
        fun formatted(): String = SimpleDateFormat("mm:ss", Locale.getDefault()).format(counter)
    }
}
