package com.example.practicumhomework_2

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

class App : Application() {

    lateinit var preferences: Preferences

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = this.getSharedPreferences("my_prefs", MODE_PRIVATE)
        preferences = Preferences(sharedPreferences)
        val isDarkTheme = preferences.getCurrentTheme()
        switchTheme(isDarkTheme)
    }
    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}