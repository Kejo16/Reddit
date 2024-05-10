package com.example.reddit.ui.home

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reddit.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by inject<HomeViewModel>()

    private var binding: ActivityMainBinding? = null
    private val adapter: PostAdapter by lazy {
        PostAdapter(
            onClick = { url -> openUrl(url) },
            onLongClick = { url -> downloadImage(url) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupRecyclerView()
        observePosts()
    }

    private fun observePosts() {
        viewModel.redditPosts.observe(this) { redditPosts ->
            binding?.progress?.isVisible = redditPosts.isEmpty()
            adapter.submitList(redditPosts)
        }
    }

    private fun setupRecyclerView() {
        binding?.recyclerPost?.apply {
            adapter = this@HomeActivity.adapter.apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
            addOnScrollListener(
                object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                        viewModel.onScrolled(lastVisibleItemPosition)
                    }
                }
            )
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private fun downloadImage(url: String) {
        val request = DownloadManager.Request(url.toUri()).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("RedditImage")
            setDescription("Downloading image from Reddit")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager
        downloadManager?.enqueue(request)
        Toast.makeText(this@HomeActivity, "Downloading image...", Toast.LENGTH_SHORT).show()
    }
}