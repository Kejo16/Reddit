package com.example.reddit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Post(
    val id: String?,
    val author: String?,
    val title: String?,
    val hoursAgo: Long,
    val thumbnail: String?,
    val commentsCount: Int,
    val image: String?,
)

fun TopResponse.PostData.toPost(): Post {
    val createdAt = createdAt.toInt()
    val hours = System.currentTimeMillis() - createdAt / 1000 / 3600
    return Post(
        id = id,
        author = author,
        title = title,
        hoursAgo = hours,
        thumbnail = thumbnail,//preview?.images?.get(0)?.resolutions?.get(0)?.url,
        commentsCount = commentsCount,
        image = image //preview?.images?.get(0)?.source?.url
    )
}

@Serializable
data class TopResponse(
    @SerialName("data") val data: ResponseData
) {
    @Serializable
    data class ResponseData(
        @SerialName("children") val children: List<PostData>
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

    ) /*{
        @Serializable
        data class Preview(
            @SerialName("images") val images: List<ImageData> = emptyList()
        )

        @Serializable
        data class ImageData(
            @SerialName("source") val source: Source?,
            @SerialName("resolutions") val resolutions: List<Resolution> = emptyList()
        )

        @Serializable
        data class Source(
            @SerialName("url") val url: String? = null
        )

        @Serializable
        data class Resolution(
            @SerialName("url") val url: String? = null
        )
    }*/
}


