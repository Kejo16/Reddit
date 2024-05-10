package com.example.reddit.mapper

import com.example.reddit.data.network.response.TopResponse
import com.example.reddit.model.Post

fun TopResponse.PostData.toPost(): Post {
    val createdAt = createdAt.toLong()
    val currentSeconds = System.currentTimeMillis() / 1000
    val hours = (currentSeconds - createdAt) / 3600
    return Post(
        id = id,
        author = author,
        title = title,
        hoursAgo = hours,
        thumbnail = thumbnail,
        commentsCount = commentsCount,
        image = image
    )
}