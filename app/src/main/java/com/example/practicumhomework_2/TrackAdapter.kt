package com.example.practicumhomework_2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackAdapter : RecyclerView.Adapter<TrackViewHolder>() {
    var tracks: List<Track> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.track_item_layout, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        TrackViewHolder(holder.itemView).bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrackList(trackList: List<Track>) {
        tracks = trackList
        notifyDataSetChanged()
    }
}
