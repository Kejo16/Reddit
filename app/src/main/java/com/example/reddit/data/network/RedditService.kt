package com.example.reddit.data.network

import com.example.reddit.data.network.response.TopResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("top.json")
    suspend fun getTopPosts(
        @Query("after") after: String? = null,
        @Query("limit") limit: Int,
    ): TopResponse
}