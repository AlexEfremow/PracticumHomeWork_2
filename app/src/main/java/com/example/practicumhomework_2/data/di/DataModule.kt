package com.example.practicumhomework_2.data.di

import androidx.room.Room
import com.example.practicumhomework_2.data.db.AppDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "trackDataBase")
            .build()
    }
}