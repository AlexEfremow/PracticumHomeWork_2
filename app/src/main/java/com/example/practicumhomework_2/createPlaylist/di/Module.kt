package com.example.practicumhomework_2.createPlaylist.di

import com.example.practicumhomework_2.createPlaylist.data.ImageSaver
import com.example.practicumhomework_2.createPlaylist.data.PlaylistDao
import com.example.practicumhomework_2.createPlaylist.data.PlaylistRepositoryImpl
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistInteractor
import com.example.practicumhomework_2.createPlaylist.domain.PlaylistRepository
import com.example.practicumhomework_2.createPlaylist.presentation.PlaylistCreateViewModel
import com.example.practicumhomework_2.data.db.AppDataBase
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val playlistCreateModule = module {
    factory {
        ImageSaver(androidApplication())
    }
    factory {
        PlaylistInteractor(get())
    }
    factory {
        PlaylistRepositoryImpl(get(),get())
    } bind PlaylistRepository::class
    factory {
        get<AppDataBase>().playlistDao()
    } bind PlaylistDao::class
    factory {
        Gson()
    }
    viewModelOf(::PlaylistCreateViewModel)
}