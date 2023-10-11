package com.example.practicumhomework_2.di

import com.example.practicumhomework_2.App
import org.koin.dsl.module

val module = module {
    single{
        App()
    }
}