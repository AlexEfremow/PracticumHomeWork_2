package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.search.domain.TrackSearchWrapper

class PlayerInteractor(private val searchWrapper: TrackSearchWrapper) {

    fun searchTrack(trackId: String, callback: SingleTrackSearchCallBack) {
        searchWrapper.searchSingleTrack(trackId, callback)
    }
}