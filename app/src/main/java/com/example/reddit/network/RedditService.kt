package com.example.reddit.network

import com.example.reddit.TopResponse
import retrofit2.http.GET

interface RedditService {
    @GET("top.json")
    suspend fun getTopPosts(): TopResponse
}