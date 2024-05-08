package com.example.reddit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.reddit.Post
import com.example.reddit.databinding.FragmentHomeBinding

class FragmentHome: Fragment() {
    private val adapter: PostAdapter by lazy { PostAdapter(
        {

        }
    )}
    private var binding: FragmentHomeBinding? = null
    private val list = listOf(
        Post(
            id = "12ascsa123",
            title = "Hello title",
            author = "/r artur",
            thumbnail = "https://media.4-paws.org/5/b/4/b/5b4b5a91dd9443fa1785ee7fca66850e06dcc7f9/VIER%20PFOTEN_2019-12-13_209-2890x2000-1920x1329.jpg",
            hoursAgo = 12,
            commentsCount = 415
        ),

        Post(
            id = "345scsa123",
            title = "Good bye, title",
            author = "/r artur",
            thumbnail = "https://media.4-paws.org/5/b/4/b/5b4b5a91dd9443fa1785ee7fca66850e06dcc7f9/VIER%20PFOTEN_2019-12-13_209-2890x2000-1920x1329.jpg",
            hoursAgo = 12,
            commentsCount = 415
        )
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.recyclerPost?.adapter = adapter
        adapter.submitList(list)
    }
}