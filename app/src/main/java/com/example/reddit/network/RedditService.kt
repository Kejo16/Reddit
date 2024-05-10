package com.example.reddit.network

import com.example.reddit.TopResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("top.json")
    suspend fun getTopPosts(
        @Query("after") after: String?,
        @Query("limit") limit: Int,
        @Query("count") count: Int
    ): TopResponse
}