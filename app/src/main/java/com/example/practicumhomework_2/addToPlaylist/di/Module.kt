package com.example.practicumhomework_2.addToPlaylist.di

import com.example.practicumhomework_2.addToPlaylist.data.PlaylistTrackDao
import com.example.practicumhomework_2.addToPlaylist.presentation.AddToPlaylistViewModel
import com.example.practicumhomework_2.data.db.AppDataBase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val playlistSmallModule = module {
    factory {
        get<AppDataBase>().playlistTrackDao()
    } bind PlaylistTrackDao::class
    viewModelOf(::AddToPlaylistViewModel)
}