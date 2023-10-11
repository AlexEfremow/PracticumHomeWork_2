package com.example.practicumhomework_2.search.di

import android.app.Application
import android.content.SharedPreferences
import com.example.practicumhomework_2.search.data.local.SearchPreferencesImpl
import com.example.practicumhomework_2.search.data.network.TrackSearchWrapperImpl
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchPreferences
import com.example.practicumhomework_2.search.domain.TrackSearchWrapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.practicumhomework_2.search.presentation.SearchViewModel
import org.koin.android.ext.koin.androidApplication


val searchModule = module {
    factory {
        SearchInteractor(get(), get())
    }
    factory<SearchPreferences> {
        SearchPreferencesImpl(get())
    }
    factory<TrackSearchWrapper> {
        TrackSearchWrapperImpl(get())
    }
    factory {
        TracksSearchApi.retrofit
    }
    single<SharedPreferences> {
        androidApplication().getSharedPreferences("my_prefs", Application.MODE_PRIVATE)
    }
    viewModelOf(::SearchViewModel)
}