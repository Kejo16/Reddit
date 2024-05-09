package com.example.reddit.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.reddit.Post
import com.example.reddit.databinding.FragmentHomeBinding
import com.example.reddit.network.RedditService
import com.example.reddit.network.RetrofitClient
import com.example.reddit.toPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentHome : Fragment() {
    private val adapter: PostAdapter by lazy { PostAdapter {
        url ->
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    } }
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val redditService = RetrofitClient.redditService
        lifecycleScope.launch {
            val redditPosts = getTopPosts(redditService)
            binding?.recyclerPost?.adapter = adapter
            adapter.submitList(redditPosts)
        }
    }

    private suspend fun getTopPosts(redditService: RedditService): List<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val response = redditService.getTopPosts()
                response.data.children.map {it.data.toPost()  }
            } catch (e: Exception) {
                Log.w("FragmentHome", "Помилка при отриманні публікацій з Reddit", e)
                emptyList()
            }
        }
    }
}