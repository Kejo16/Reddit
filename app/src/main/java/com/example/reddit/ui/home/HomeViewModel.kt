package com.example.reddit.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddit.Post
import com.example.reddit.network.RetrofitClient
import com.example.reddit.toPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val redditService = RetrofitClient.redditService
    private val _redditPosts = MutableLiveData<List<Post>>()
    val redditPosts: LiveData<List<Post>> = _redditPosts

    private var after: String? = null
    private var count = 0
    private val limit = 25

    fun loadRedditPosts() {
        viewModelScope.launch {
            try {
                val posts = getTopPosts(after, count, limit)
                _redditPosts.value = posts
            } catch (e: Exception) {
                Log.w("HomeViewModel", "Failed to load Reddit posts", e)
            }
        }
    }

    private suspend fun getTopPosts(after: String?, count: Int, limit: Int): List<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val response = redditService.getTopPosts(
                    after = after,
                    limit = limit,
                    count = count
                )
                this@HomeViewModel.after = response.data.after
                this@HomeViewModel.count += response.data.children.size
                response.data.children.map { it.data.toPost() }
            } catch (e: Exception) {
                Log.w("HomeViewModel", "Failed to get top Reddit posts", e)
                emptyList()
            }
        }
    }
}
