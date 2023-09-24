package com.example.practicumhomework_2

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.practicumhomework_2.search.data.local.SearchPreferencesImpl
import com.example.practicumhomework_2.search.data.local.SearchPreferences
import com.example.practicumhomework_2.settings.data.SettingsPreferences
import com.example.practicumhomework_2.settings.data.SettingsPreferencesImpl

class App : Application() {

    lateinit var searchPreferences: SearchPreferences
    lateinit var settingsPreferences: SettingsPreferences

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = this.getSharedPreferences("my_prefs", MODE_PRIVATE)
        searchPreferences = SearchPreferencesImpl(sharedPreferences)
        settingsPreferences = SettingsPreferencesImpl(sharedPreferences)
        val isDarkTheme = settingsPreferences.getCurrentTheme()
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