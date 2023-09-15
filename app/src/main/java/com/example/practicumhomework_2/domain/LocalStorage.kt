package com.example.practicumhomework_2.domain

import com.example.practicumhomework_2.domain.entity.Track

interface LocalStorage {
    fun save(track: Track)

    fun getTrackList(): List<Track>

    fun clearHistory()

    fun getCurrentTheme(): Boolean

    fun saveTheme(isDarkTheme: Boolean)
}