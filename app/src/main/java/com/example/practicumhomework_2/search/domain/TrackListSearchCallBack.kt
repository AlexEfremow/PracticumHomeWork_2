package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.entity.Track

interface TrackListSearchCallBack {

    fun onSuccess(data: List<Track>)

    fun onError(message: String)
}