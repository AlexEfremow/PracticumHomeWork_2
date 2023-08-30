package com.example.practicumhomework_2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.remote.TrackSearchResponse
import com.example.practicumhomework_2.remote.TracksSearchApi
import com.google.android.material.resources.MaterialResources.getDimensionPixelSize
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.callback.Callback

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_player)

        val trackNameTextView = findViewById<TextView>(R.id.track_name)
        val musicianNameTextView = findViewById<TextView>(R.id.artist_name)
        val trackTimeTextView = findViewById<TextView>(R.id.track_time)
        val trackDurationTextView = findViewById<TextView>(R.id.track_duration_value)
        val albumTextView = findViewById<TextView>(R.id.album_value)
        val trackReleaseYearTextView = findViewById<TextView>(R.id.track_release_year_value)
        val trackGenreTextView = findViewById<TextView>(R.id.track_genre_value)
        val trackCountryTextView = findViewById<TextView>(R.id.track_country_value)

        val trackId = intent.getStringExtra("track_id") ?: ""

        val searchTrackCallBack = object : retrofit2.Callback<TrackSearchResponse> {

            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                if (response.isSuccessful) {

                    val track = response.body()?.results?.first() ?: return
                    trackNameTextView.text = track.trackName
                    musicianNameTextView.text = track.artistName
                    trackDurationTextView.text = track.timeFormat()
                    albumTextView.text = track.collectionName
                    trackReleaseYearTextView.text = track.releaseDate.take(4)
                    trackGenreTextView.text = track.primaryGenreName
                    trackCountryTextView.text = track.country


                    Glide.with(this@PlayerActivity)
                        .load(track.getCoverArtwork())
                        .transform(
                            CenterInside(),
                            RoundedCorners(resources.getDimensionPixelSize(R.dimen.track_poster_corner_radius))
                        )
                        .placeholder(R.drawable.placeholder)
                        .into(findViewById(R.id.track_poster))
                }
            }


            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {}

        }

        TracksSearchApi.retrofit.searchTracks(trackId).enqueue(searchTrackCallBack)

        findViewById<FrameLayout>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}