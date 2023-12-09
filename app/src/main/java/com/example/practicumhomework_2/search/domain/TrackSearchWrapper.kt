package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.PlayerState

interface TrackSearchWrapper {
    suspend fun searchTracks(value: String): SearchState
    suspend fun searchSingleTrack(trackId: String): PlayerState
}