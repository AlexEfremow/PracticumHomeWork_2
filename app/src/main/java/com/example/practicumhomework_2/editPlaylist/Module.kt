package com.example.practicumhomework_2.editPlaylist


import com.example.practicumhomework_2.editPlaylist.presentation.EditPlaylistViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val editPlaylistModule = module {
    viewModelOf(::EditPlaylistViewModel)
}