package com.example.practicumhomework_2

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicumhomework_2.player.di.playerModule
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.presentation.PlayerViewModel
import com.example.practicumhomework_2.search.data.local.SearchPreferencesImpl
import com.example.practicumhomework_2.search.data.network.TrackSearchWrapperImpl
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.di.searchModule
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchPreferences
import com.example.practicumhomework_2.search.presentation.SearchViewModel
import com.example.practicumhomework_2.settings.data.SettingsPreferences
import com.example.practicumhomework_2.settings.data.SettingsPreferencesImpl
import com.example.practicumhomework_2.settings.di.settingsModule
import com.example.practicumhomework_2.settings.domain.SettingsInteractor
import com.example.practicumhomework_2.settings.presentation.SettingsViewModel
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(playerModule, searchModule, settingsModule))
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