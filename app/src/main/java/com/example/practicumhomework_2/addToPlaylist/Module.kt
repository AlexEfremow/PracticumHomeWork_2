package com.example.practicumhomework_2.addToPlaylist

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val playlistSmallModule = module {
    viewModelOf(::AddToPlaylistViewModel)
}