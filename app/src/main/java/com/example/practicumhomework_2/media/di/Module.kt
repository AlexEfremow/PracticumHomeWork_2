package com.example.practicumhomework_2.media.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.example.practicumhomework_2.media.presentation.PlaylistsViewModel
import com.example.practicumhomework_2.media.presentation.FavouriteTracksViewModel

val mediaModule = module {
    viewModelOf(::FavouriteTracksViewModel)
    viewModelOf(::PlaylistsViewModel)
}