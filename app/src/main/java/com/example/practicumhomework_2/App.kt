package com.example.practicumhomework_2

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.presentation.PlayerViewModel
import com.example.practicumhomework_2.search.data.local.SearchPreferencesImpl
import com.example.practicumhomework_2.search.data.network.TrackSearchWrapperImpl
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchPreferences
import com.example.practicumhomework_2.search.presentation.SearchViewModel
import com.example.practicumhomework_2.settings.data.SettingsPreferences
import com.example.practicumhomework_2.settings.data.SettingsPreferencesImpl
import com.example.practicumhomework_2.settings.domain.SettingsInteractor
import com.example.practicumhomework_2.settings.presentation.SettingsViewModel

class App : Application() {

    lateinit var searchPreferences: SearchPreferences
    lateinit var settingsPreferences: SettingsPreferences
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = this.getSharedPreferences("my_prefs", MODE_PRIVATE)
        searchPreferences = SearchPreferencesImpl(sharedPreferences)
        settingsPreferences = SettingsPreferencesImpl(sharedPreferences)
        viewModelFactory = ViewModelFactory(
            searchPreferences,
            settingsPreferences,
            PlayerInteractor(TrackSearchWrapperImpl(TracksSearchApi.retrofit))
        )
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

class ViewModelFactory(
    private val searchPreferences: SearchPreferences,
    private val settingsPreferences: SettingsPreferences,
    private val playerInteractor: PlayerInteractor
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            PlayerViewModel::class.java -> {
                PlayerViewModel(playerInteractor)
            }
            SearchViewModel::class.java -> {
                SearchViewModel(
                    SearchInteractor(
                        searchPreferences, TrackSearchWrapperImpl(
                            TracksSearchApi.retrofit
                        )
                    )
                )
            }
            SettingsViewModel::class.java -> {
                SettingsViewModel(SettingsInteractor(settingsPreferences))
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
        return viewModel as T
    }
}