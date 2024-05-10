package com.example.reddit.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.reddit.databinding.ItemPostBinding
import com.example.reddit.model.Post

class PostAdapter(
    private val onClick: (url: String) -> Unit,
    private val onLongClick: (url: String) -> Unit
) : ListAdapter<Post, PostViewHolder>(PostItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(
            binding = binding,
            onClick = onClick,
            onLongClick = onLongClick
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}