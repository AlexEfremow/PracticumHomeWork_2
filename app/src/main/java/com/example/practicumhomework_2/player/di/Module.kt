package com.example.practicumhomework_2.player.di

import com.example.practicumhomework_2.player.data.FavoriteTracksRepositoryImpl
import com.example.practicumhomework_2.player.domain.FavoriteTracksRepository
import com.example.practicumhomework_2.player.domain.FavouriteTracksInteractor
import com.example.practicumhomework_2.player.domain.FavouriteTracksInteractorImpl
import com.example.practicumhomework_2.player.domain.PlayerInteractor
import com.example.practicumhomework_2.player.presentation.PlayerViewModel
import com.example.practicumhomework_2.search.data.network.TrackSearchRepositoryImpl
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.domain.TrackSearchRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val playerModule = module {
    factory {
        PlayerInteractor(get())
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<TracksSearchApi>()
    }
    single {
        FavoriteTracksRepositoryImpl(get())
    } bind FavoriteTracksRepository::class
    viewModelOf(::PlayerViewModel)
    single {
        FavouriteTracksInteractorImpl(get())
    } bind FavouriteTracksInteractor::class
}