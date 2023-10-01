package com.example.practicumhomework_2.search.domain

import com.example.practicumhomework_2.player.domain.entity.Track

interface SearchPreferences {
    fun save(track: Track)

    fun getTrackList(): List<Track>

    fun clearHistory()
}