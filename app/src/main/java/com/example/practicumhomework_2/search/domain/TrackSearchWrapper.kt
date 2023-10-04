package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.SingleTrackSearchCallBack

interface TrackSearchWrapper {
    fun searchTracks(value: String, callback: TrackListSearchCallBack)
    fun searchSingleTrack(trackId: String, callBack: SingleTrackSearchCallBack)
}