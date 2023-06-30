package com.example.practicumhomework_2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrackAdapter: RecyclerView.Adapter<TrackViewHolder>() {
    private var tracks: List<Track> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_item_layout, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val trackNameTextView = holder.itemView.findViewById<TextView>(R.id.track_name)
        val musicianNameTextView = holder.itemView.findViewById<TextView>(R.id.artist_name)
        val trackTimeTextView = holder.itemView.findViewById<TextView>(R.id.track_time)
        trackNameTextView.text = tracks[position].trackName
        musicianNameTextView.text = tracks[position].artistName
        trackTimeTextView.text = tracks[position].trackTime
        Glide.with(holder.itemView.context).load(tracks[position].artworkUrl).into(holder.itemView.findViewById(R.id.image_url));
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