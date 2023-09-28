package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.player.domain.entity.Track

sealed class PlayerState {
    class Error(val message: String): PlayerState()
    class TrackLoaded(val track: Track): PlayerState()
}
