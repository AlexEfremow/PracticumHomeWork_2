package com.example.practicumhomework_2.addToPlaylist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicumhomework_2.databinding.PlaylistItemSmallBinding
import com.example.practicumhomework_2.media.domain.PlaylistModel

class AddToPlaylistAdapter(private val onClick: (Int)-> Unit): RecyclerView.Adapter<AddToPlaylistViewHolder>() {
    private var playlists: List<PlaylistModel> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddToPlaylistViewHolder {
        val binding = PlaylistItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddToPlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddToPlaylistViewHolder, position: Int) {
        val item = playlists[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return playlists.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updatePlaylists(playlistsList: List<PlaylistModel>) {
        playlists = playlistsList
        notifyDataSetChanged()
    }

}