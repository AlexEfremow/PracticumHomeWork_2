package com.example.practicumhomework_2.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.practicumhomework_2.settings.data.SettingsPreferences
import com.example.practicumhomework_2.settings.domain.SettingsInteractor

class SettingsViewModelFactory(private val settingsPreferences: SettingsPreferences): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == SettingsViewModel::class.java) {
            return SettingsViewModel(SettingsInteractor(settingsPreferences)) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}