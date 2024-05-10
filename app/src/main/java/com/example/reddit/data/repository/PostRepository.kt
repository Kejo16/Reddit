package com.example.reddit.data.repository

import com.example.reddit.model.Post

interface PostRepository {

    suspend fun getTopPosts(page: Int): List<Post>
}