package com.example.reddit.ui.home

import android.annotation.SuppressLint
import android.view.View
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
            authorTextView.text = "r/" + post.author
            hoursTextView.text = "•${post.hoursAgo} hr. ago"
            titleTextView.text = post.title
            commentCountTextView.text = post.commentsCount.toString()
            if(post.thumbnail.isNullOrBlank()){
                thumbnailImageView.visibility = View.GONE
            }else{
                loadThumbnail(post.thumbnail)
            }

            thumbnailImageView.setOnClickListener{
                val url = post.image ?: return@setOnClickListener
                onClick(url)
            }
        }
    }

    private fun loadThumbnail(url: String?) {
        url?.let {
            Glide.with(binding.root)
                .load(it)
                .into(binding.thumbnailImageView)
        }
    }
}