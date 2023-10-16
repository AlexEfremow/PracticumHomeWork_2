package com.example.practicumhomework_2.player.di

import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.presentation.PlayerViewModel
import com.example.practicumhomework_2.search.data.network.TrackSearchWrapperImpl
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.domain.TrackSearchWrapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val playerModule = module {
    factory {
        PlayerInteractor(get())
    }
    factory<TrackSearchWrapper> {
        TrackSearchWrapperImpl(get())
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<TracksSearchApi>()
    }
    viewModelOf(::PlayerViewModel)
}