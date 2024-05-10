package com.example.reddit.data.repository.model

import com.example.reddit.model.Post

data class CachedPost(
    val after: String?,
    val posts: List<Post>,
)