package com.example.practicumhomework_2.media.domain

import com.example.practicumhomework_2.player.domain.entity.Track

sealed class FavoriteTracksState {
    object Empty : FavoriteTracksState()
    class NotEmpty(val trackList: List<Track>): FavoriteTracksState()
}
