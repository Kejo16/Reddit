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

    fun loadRedditPosts() {
        viewModelScope.launch {
            val posts = getTopPosts()
            _redditPosts.value = posts
        }
    }

    private suspend fun getTopPosts(): List<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val response = redditService.getTopPosts()
                response.data.children.map { it.data.toPost() }
            } catch (e: Exception) {
                Log.w("HomeViewModel", "Помилка при отриманні публікацій з Reddit", e)
                emptyList()
            }
        }
    }
}
