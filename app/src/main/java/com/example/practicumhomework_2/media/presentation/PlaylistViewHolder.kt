package com.example.practicumhomework_2.media.presentation

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.PlaylistItemBinding
import com.example.practicumhomework_2.media.domain.PlaylistModel

class PlaylistViewHolder(private val binding: PlaylistItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PlaylistModel) {
        binding.playlistCover.setImageURI(Uri.parse(item.cover))
        binding.playlistName.text = item.name
        binding.playlistTracksCount.text = item.count.toString()
//        Glide.with(binding.playlistCover)
//            .load(item.cover)
//            .transform(
//                CenterCrop(),
//                RoundedCorners(binding.root.context.resources.getDimensionPixelSize(R.dimen.track_cover_corner_radius))
//            )
//            .into(binding.playlistCover)
    }
}