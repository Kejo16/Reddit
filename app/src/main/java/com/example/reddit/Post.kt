package com.example.reddit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Post(
    val id: String,
    val author: String,
    val title: String,
    val hoursAgo: Long,
    val thumbnail: String? = null,
    val commentsCount: Int,
    val image: String? = null,
)

fun TopResponse.PostData.toPost(): Post {
    val createdAt = createdAt.toInt()
    val hours = System.currentTimeMillis() - createdAt / 1000 / 3600
    return Post(
        id = "",
        author = author,
        title = "",
        hoursAgo = hours,
        thumbnail = preview?.images?.get(0)?.resolutions?.get(0)?.url,
        commentsCount = commentsQuantity,
        image = preview?.images?.get(0)?.source?.url
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
        @SerialName("author_fullname")
        val author: String,
        @SerialName("created")
        val createdAt: String,
        @SerialName("num_comments")
        val commentsQuantity: Int,
        @SerialName("preview")
        val preview: Preview?
    ) {
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
    }
}
