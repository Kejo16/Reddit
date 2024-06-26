package com.example.reddit.ui.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.reddit.databinding.ItemPostBinding
import com.example.reddit.model.Post

class PostViewHolder(
    private val binding: ItemPostBinding,
    private val onClick: (url: String) -> Unit,
    private val onLongClick: (url: String) -> Unit
) : ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(post: Post) {
        with(binding) {
            authorTextView.text = "r/" + post.author
            hoursTextView.text = "•${post.hoursAgo} hr. ago"
            titleTextView.text = post.title
            commentCountTextView.text = post.commentsCount.toString()
            post.thumbnail?.let { loadThumbnail(it) }
            thumbnailImageView.setOnClickListener {
                val url = post.image ?: return@setOnClickListener
                onClick(url)
            }
            thumbnailImageView.setOnLongClickListener {
                val url = post.image ?: return@setOnLongClickListener false
                onLongClick(url)
                true
            }
        }
    }

    private fun loadThumbnail(url: String) {
        Glide.with(binding.root)
            .load(url)
            .into(binding.thumbnailImageView)
    }
}