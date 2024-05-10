package com.example.reddit.model

data class Post(
    val id: String?,
    val author: String?,
    val title: String?,
    val hoursAgo: Long,
    val thumbnail: String?,
    val commentsCount: Int,
    val image: String?,
)