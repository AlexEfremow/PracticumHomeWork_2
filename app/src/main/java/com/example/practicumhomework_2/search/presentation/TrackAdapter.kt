package com.example.practicumhomework_2.search.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.player.domain.entity.Track

class TrackAdapter(private val onClick: (Track) -> Unit, private val onLongClick: (Track) -> Unit = {}) : RecyclerView.Adapter<TrackViewHolder>() {

    var tracks: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.track_item_layout, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = tracks[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
        holder.itemView.setOnLongClickListener {
            onLongClick(item)
            true
        }
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
