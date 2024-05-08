package com.example.reddit.ui.home

import android.annotation.SuppressLint
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.reddit.Post
import com.example.reddit.databinding.ItemPostBinding

class PostViewHolder(
    private val binding: ItemPostBinding,
    private val onClick: (url: String) -> Unit
) : ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(post: Post) {
        with(binding) {
            titleTextView.text = post.title
            authorTextView.text = post.author
            hoursTextView.text = "${post.hoursAgo} hours ago"
            commentCountTextView.text = post.commentsCount.toString()
            thumbnailImageView.setImageURI(post.thumbnail?.toUri());
            post.thumbnail?.let { loadThumbnail(it) }
            root.setOnClickListener {
                val url = post.image ?: return@setOnClickListener
                onClick(url)
            }
        }

    }

    private fun loadThumbnail(url: String) {
        Glide.with(binding.root)
            .load(url)
            .into(binding.thumbnailImageView)
    }
}