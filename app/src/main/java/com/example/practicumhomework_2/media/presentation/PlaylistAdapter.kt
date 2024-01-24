package com.example.practicumhomework_2.media.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicumhomework_2.createPlaylist.data.entity.PlaylistEntity
import com.example.practicumhomework_2.media.domain.PlaylistModel
import com.example.practicumhomework_2.databinding.PlaylistItemBinding

class PlaylistAdapter : RecyclerView.Adapter<PlaylistViewHolder>() {

    private var playlists: List<PlaylistModel> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding = PlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val item = playlists[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePlaylists(list: List<PlaylistModel>) {
        playlists = list
        notifyDataSetChanged()
    }
}