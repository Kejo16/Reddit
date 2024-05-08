package com.example.reddit.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.reddit.Post
import com.example.reddit.databinding.ItemPostBinding

class PostAdapter(
    private val onClick: (url: String) -> Unit
): ListAdapter<Post, PostViewHolder>(PostItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(
            binding = binding,
            onClick = onClick
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}