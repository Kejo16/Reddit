package com.example.reddit

import android.app.Application
import com.example.reddit.di.networkModule
import com.example.reddit.di.repositoryModule
import com.example.reddit.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RedditApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val modules = listOf(networkModule, repositoryModule, viewModelModule)
        startKoin {
            androidContext(this@RedditApp)
            modules(modules)
        }
    }
}