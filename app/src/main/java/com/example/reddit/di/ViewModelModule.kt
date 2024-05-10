package com.example.reddit.di

import com.example.reddit.ui.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { HomeViewModel(get()) }
}