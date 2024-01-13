package com.example.practicumhomework_2.createPlaylist.di

import com.example.practicumhomework_2.createPlaylist.data.ImageSaver
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val playlistCreateModule = module {
    factory {
        ImageSaver(androidApplication())
    }
}