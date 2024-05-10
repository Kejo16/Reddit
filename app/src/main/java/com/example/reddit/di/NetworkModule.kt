package com.example.reddit.di

import com.example.reddit.data.network.RedditService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://www.reddit.com/"

val networkModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            explicitNulls = false
            encodeDefaults = true
        }
    }
    single<RedditService> { createRedditService(get()) }
}

private fun createRedditService(json: Json): RedditService {
    val converter = json.asConverterFactory("application/json".toMediaType())
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converter)
        .build()
        .create(RedditService::class.java)
}