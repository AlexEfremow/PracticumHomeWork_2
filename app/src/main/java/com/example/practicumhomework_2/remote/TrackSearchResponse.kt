package com.example.practicumhomework_2.remote

import com.example.practicumhomework_2.Track

data class TrackSearchResponse(val resultCount: Int, val results: List<Track>)
