package com.example.practicumhomework_2.media.presentation

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.PlaylistItemBinding
import com.example.practicumhomework_2.media.domain.PlaylistModel

class PlaylistViewHolder(
    private val binding: PlaylistItemBinding,
    private val onClick: ()-> Unit
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlaylistModel) {
        binding.root.setOnClickListener {
            onClick.invoke()
        }
        binding.playlistName.text = item.name
        binding.playlistTracksCount.text = itemView.resources.getQuantityString(R.plurals.tracks_count, item.count, item.count)
        Glide.with(binding.playlistCover)
            .load(item.cover)
            .transform(
                CenterCrop(),
                RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.track_poster_corner_radius))
            )
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.playlistCover)
    }
}