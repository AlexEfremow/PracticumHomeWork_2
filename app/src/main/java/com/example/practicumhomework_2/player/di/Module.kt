package com.example.practicumhomework_2.player.di

import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.presentation.PlayerViewModel
import com.example.practicumhomework_2.search.data.network.TrackSearchWrapperImpl
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.domain.TrackSearchWrapper
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val playerModule = module {
    factory {
        PlayerInteractor(get())
    }
    factory<TrackSearchWrapper> {
        TrackSearchWrapperImpl(get())
    }
    factory {
        TracksSearchApi.retrofit
    }
    viewModelOf(::PlayerViewModel)
}