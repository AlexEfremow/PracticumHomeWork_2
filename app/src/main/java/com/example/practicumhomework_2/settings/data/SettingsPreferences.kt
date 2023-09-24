package com.example.practicumhomework_2.settings.data

interface SettingsPreferences {
    fun getCurrentTheme(): Boolean

    fun saveTheme(isDarkTheme: Boolean)
}