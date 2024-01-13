package com.example.practicumhomework_2

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.practicumhomework_2.createPlaylist.di.playlistCreateModule
import com.example.practicumhomework_2.data.di.dataModule
import com.example.practicumhomework_2.media.di.mediaModule
import com.example.practicumhomework_2.player.di.playerModule
import com.example.practicumhomework_2.search.di.searchModule
import com.example.practicumhomework_2.settings.data.SettingsPreferences
import com.example.practicumhomework_2.settings.di.settingsModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(playerModule, searchModule, settingsModule, dataModule, mediaModule, playlistCreateModule))
        }
        val settingsPreferences: SettingsPreferences by inject()
        val isDarkTheme = settingsPreferences.getCurrentTheme()
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}