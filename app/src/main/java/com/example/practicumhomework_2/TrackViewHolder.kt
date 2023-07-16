package com.example.practicumhomework_2

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TrackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val trackNameTextView = itemView.findViewById<TextView>(R.id.track_name)
    private val musicianNameTextView = itemView.findViewById<TextView>(R.id.artist_name)
    private val trackTimeTextView = itemView.findViewById<TextView>(R.id.track_time)

    fun bind(track: Track) {
        trackNameTextView.text = track.trackName
        musicianNameTextView.text = track.artistName
//        trackTimeTextView.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTime)
        trackTimeTextView.text = LocalTime.ofSecondOfDay(track.trackTime).format(DateTimeFormatter.ofPattern("mm:ss")) // TODO: fix time issue
        Glide.with(itemView.context)
            .load(track.artworkUrl)
            .transform(CenterInside(), RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.track_cover_corner_radius)))
            .placeholder(R.drawable.placeholder)
            .into(itemView.findViewById(R.id.image_url))
    }
}