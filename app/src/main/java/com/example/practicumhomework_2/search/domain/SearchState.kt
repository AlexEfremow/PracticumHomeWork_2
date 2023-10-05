package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.entity.Track

sealed class SearchState{
    class Success(val trackList: List<Track>): SearchState()
    class Error(val message: String): SearchState()
}
