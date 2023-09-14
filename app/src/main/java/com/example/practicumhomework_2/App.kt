package com.example.practicumhomework_2

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.practicumhomework_2.data.local.Preferences
import com.example.practicumhomework_2.domain.LocalStorage

class App : Application() {

    lateinit var preferences: LocalStorage

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