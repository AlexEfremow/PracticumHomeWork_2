package com.example.practicumhomework_2.settings.domain

import com.example.practicumhomework_2.settings.data.SettingsPreferences

class SettingsInteractor(private val preferences: SettingsPreferences) {
    val currentTheme = preferences.getCurrentTheme()
    fun saveTheme(darkThemeEnabled: Boolean) {
        preferences.saveTheme(darkThemeEnabled)
    }
}