package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.search.domain.TrackSearchRepository

class PlayerInteractor(private val searchWrapper: TrackSearchRepository) {

    suspend fun searchTrack(trackId: String): PlayerState {
        return searchWrapper.searchSingleTrack(trackId)
    }
}