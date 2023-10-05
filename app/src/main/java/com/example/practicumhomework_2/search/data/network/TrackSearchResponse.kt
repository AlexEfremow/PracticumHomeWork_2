package com.example.practicumhomework_2.search.data.network

import com.example.practicumhomework_2.player.domain.entity.Track

data class TrackSearchResponse(val resultCount: Int, val results: List<Track>)
