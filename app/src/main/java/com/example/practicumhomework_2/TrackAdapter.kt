package com.example.practicumhomework_2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackAdapter: RecyclerView.Adapter<TrackViewHolder>() {
     var tracks: List<Track> = emptyList()
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
        Glide.with(holder.itemView.context)
            .load(tracks[position].artworkUrl)
            .transform(CenterInside(), RoundedCorners(8))
            .error(R.drawable.error)
            .placeholder(R.drawable.error)
            .into(holder.itemView.findViewById(R.id.image_url))
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
