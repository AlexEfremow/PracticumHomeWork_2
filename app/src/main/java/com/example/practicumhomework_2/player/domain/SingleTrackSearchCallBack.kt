package com.example.practicumhomework_2.player.domain

import com.example.practicumhomework_2.player.domain.entity.Track

interface SingleTrackSearchCallBack {

    fun onSuccess(data: Track)

    fun onError(message: String)
}