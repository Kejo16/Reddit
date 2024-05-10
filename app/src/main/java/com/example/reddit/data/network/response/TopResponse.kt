package com.example.reddit.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopResponse(
    @SerialName("data") val data: ResponseData
) {
    @Serializable
    data class ResponseData(
        @SerialName("after")
        val after: String?,
        @SerialName("children") val children: List<InnerData>
    )

    @Serializable
    data class InnerData(
        @SerialName("data") val data: PostData
    )

    @Serializable
    data class PostData(
        @SerialName("id")
        val id: String,
        @SerialName("subreddit")
        val author: String,
        @SerialName("title")
        val title: String,
        @SerialName("created")
        val createdAt: Double,
        @SerialName("thumbnail")
        val thumbnail: String,
        @SerialName("num_comments")
        val commentsCount: Int,
        @SerialName("url")
        val image: String

    )
}