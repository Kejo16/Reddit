package com.example.reddit.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reddit.data.repository.PostRepository
import com.example.reddit.model.Post
import kotlinx.coroutines.launch

class HomeViewModel(
    private val postRepository: PostRepository
) : ViewModel() {

    private val _redditPosts = MutableLiveData<List<Post>>()
    val redditPosts: LiveData<List<Post>> = _redditPosts

    init {
        loadRedditPosts(0)
    }

    fun onScrolled(lastVisibleItemPosition: Int) {
        val page = lastVisibleItemPosition / PAGE_PRELOAD_SIZE
        loadRedditPosts(page)
    }

    private fun loadRedditPosts(page: Int) {
        viewModelScope.launch {
            val posts = postRepository.getTopPosts(page)
            _redditPosts.postValue(posts)
        }
    }

    private companion object {
        const val PAGE_PRELOAD_SIZE = 20
    }
}
