package com.example.reddit.ui.home

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: PostAdapter by lazy { PostAdapter(
        onClick = { url -> openUrl(url) },
        onLongClick = { url -> downloadImage(url) }
    ) }

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

        binding?.recyclerPost?.adapter = adapter

        viewModel.redditPosts.observe(viewLifecycleOwner) { redditPosts ->
            adapter.submitList(redditPosts)
        }

        if (savedInstanceState == null) {
            viewModel.loadRedditPosts()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private fun downloadImage(url: String) {
        val context = requireContext()
        val request = DownloadManager.Request(url.toUri())
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("RedditImage")
        request.setDescription("Downloading image from Reddit")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
        Toast.makeText(context, "Downloading image...", Toast.LENGTH_SHORT).show()
    }
}