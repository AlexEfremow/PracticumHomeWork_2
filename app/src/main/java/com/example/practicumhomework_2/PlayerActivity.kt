package com.example.practicumhomework_2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.remote.TrackSearchResponse
import com.example.practicumhomework_2.remote.TracksSearchApi
import com.google.android.material.resources.MaterialResources.getDimensionPixelSize
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.callback.Callback

class PlayerActivity : AppCompatActivity() {
    companion object {
        // Число миллисекунд в одной секунде
        private const val DELAY = 1000L
    }
    lateinit var track: Track
    private var mainThreadHandler: Handler = Handler(Looper.getMainLooper())

    private var startTimerButton: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_player)
        startTimerButton = findViewById(R.id.play_button)


        val trackNameTextView = findViewById<TextView>(R.id.track_name)
        val musicianNameTextView = findViewById<TextView>(R.id.artist_name)
        val trackDurationTextView = findViewById<TextView>(R.id.track_duration_value)
        val secondsLeftTextView = findViewById<TextView>(R.id.track_time)
        val albumTextView = findViewById<TextView>(R.id.album_value)
        val trackReleaseYearTextView = findViewById<TextView>(R.id.track_release_year_value)
        val trackGenreTextView = findViewById<TextView>(R.id.track_genre_value)
        val trackCountryTextView = findViewById<TextView>(R.id.track_country_value)

        val trackId = intent.getStringExtra("track_id") ?: ""
        var counter = 0L

        fun startTimer() {
            mainThreadHandler.post(
                object : Runnable {
                    override fun run() {
                        if (counter > 0) {
                            secondsLeftTextView?.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(counter)
                            mainThreadHandler.postDelayed(this, DELAY)
                            counter-=1000
                        } else {
                            secondsLeftTextView?.text = "0:00"
                            startTimerButton?.isEnabled = true
                        }
                    }
                }
            )
        }
        startTimerButton?.setOnClickListener {
            counter = track.trackTime
            startTimer()
            startTimerButton?.isEnabled = false
            }


        val searchTrackCallBack = object : retrofit2.Callback<TrackSearchResponse> {

            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                if (response.isSuccessful) {

                    track = response.body()?.results?.first() ?: return
                    trackNameTextView.text = track.trackName
                    musicianNameTextView.text = track.artistName
                    trackDurationTextView.text = track.timeFormat()
                    secondsLeftTextView.text = track.timeFormat()
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