package com.example.practicumhomework_2.data.network

import com.example.practicumhomework_2.entity.Track

data class TrackSearchResponse(val resultCount: Int, val results: List<Track>)
