package com.example.reddit.di

import com.example.reddit.data.repository.PostRepository
import com.example.reddit.data.repository.PostRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<PostRepository> { PostRepositoryImpl(get()) }
}