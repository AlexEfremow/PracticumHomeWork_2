package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.search.domain.TrackSearchWrapper

class PlayerInteractor(private val searchWrapper: TrackSearchWrapper) {

    suspend fun searchTrack(trackId: String): PlayerState {
        return searchWrapper.searchSingleTrack(trackId)
    }
}