package com.example.practicumhomework_2.settings.di

import com.example.practicumhomework_2.settings.data.SettingsPreferences
import com.example.practicumhomework_2.settings.data.SettingsPreferencesImpl
import com.example.practicumhomework_2.settings.domain.SettingsInteractor
import com.example.practicumhomework_2.settings.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    factory {
        SettingsInteractor(get())
    }
    factory<SettingsPreferences> {
        SettingsPreferencesImpl(get())
    }
    viewModelOf(::SettingsViewModel)
}