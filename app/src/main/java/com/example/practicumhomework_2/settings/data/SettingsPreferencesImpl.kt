package com.example.practicumhomework_2.settings.data

import android.content.SharedPreferences

class SettingsPreferencesImpl(private val preferences: SharedPreferences): SettingsPreferences {
    override fun getCurrentTheme(): Boolean {
        return preferences.getBoolean(THEME_KEY, false)
    }
    override fun saveTheme(isDarkTheme: Boolean) {
        preferences.edit().putBoolean(THEME_KEY, isDarkTheme).apply()
    }
    companion object {
        const val THEME_KEY ="isDarkMode"
    }
}
