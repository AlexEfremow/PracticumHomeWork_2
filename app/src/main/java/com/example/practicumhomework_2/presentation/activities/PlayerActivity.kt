package com.example.practicumhomework_2.presentation.activities

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.domain.entity.Track
import com.example.practicumhomework_2.data.network.TrackSearchResponse
import com.example.practicumhomework_2.data.network.TracksSearchApi
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity : AppCompatActivity() {
    companion object {
        // Число миллисекунд в одной секунде
        private const val DELAY = 1000L
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }

    private lateinit var secondsLeftTextView: TextView

    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()
    private lateinit var track: Track
    private lateinit var playButton: ImageView
    private var mainThreadHandler: Handler = Handler(Looper.getMainLooper())
    private var counter = 0
    private val runnable = object : Runnable {
        override fun run() {
//            if (playerState == STATE_PLAYING) {
                secondsLeftTextView.text =
                    SimpleDateFormat("mm:ss", Locale.getDefault()).format(counter)
                mainThreadHandler.postDelayed(this, DELAY)
                counter += 1000
//            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.audio_player)
        playButton = findViewById(R.id.play_button)


        val trackNameTextView = findViewById<TextView>(R.id.track_name)
        val musicianNameTextView = findViewById<TextView>(R.id.artist_name)
        val trackDurationTextView = findViewById<TextView>(R.id.track_duration_value)
        secondsLeftTextView = findViewById(R.id.track_time)
        val albumTextView = findViewById<TextView>(R.id.album_value)
        val trackReleaseYearTextView = findViewById<TextView>(R.id.track_release_year_value)
        val trackGenreTextView = findViewById<TextView>(R.id.track_genre_value)
        val trackCountryTextView = findViewById<TextView>(R.id.track_country_value)

        val trackId = intent.getStringExtra("track_id") ?: ""

        playButton.setOnClickListener {
            playbackControl()
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
                    albumTextView.text = track.collectionName
                    preparePlayer()

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

    fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playButton.setImageResource(R.drawable.ic_baseline_play_circle_24)
            playerState = STATE_PREPARED
            mainThreadHandler.removeCallbacks(runnable)
            counter = 0
            secondsLeftTextView.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(counter)
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.pause_button)
        mainThreadHandler.post(runnable)
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.ic_baseline_play_circle_24)
        mainThreadHandler.removeCallbacks(runnable)
        playerState = STATE_PAUSED
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
        mainThreadHandler.removeCallbacks(runnable)
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}