package com.example.practicumhomework_2.playlist

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val detailedPlaylistModule = module {
    viewModelOf(::PlaylistViewModel)
}