package com.example.reddit.data.repository

import com.example.reddit.data.network.RedditService
import com.example.reddit.data.repository.model.CachedPost
import com.example.reddit.mapper.toPost
import com.example.reddit.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostRepositoryImpl(
    private val redditService: RedditService
) : PostRepository {

    private val cachedPosts = mutableMapOf<Int, CachedPost>()

    override suspend fun getTopPosts(page: Int): List<Post> = withContext(Dispatchers.IO) {
        cachedPosts[page] ?: run {
            val after = cachedPosts[page - 1]?.after
            val response = redditService.getTopPosts(after, PAGE_SIZE).data
            cachedPosts[page] = CachedPost(
                after = response.after,
                posts = response.children.map { it.data.toPost() }
            )
        }
        return@withContext cachedPosts.flatMap { it.value.posts }
    }

    private companion object {
        const val PAGE_SIZE = 25
    }
}