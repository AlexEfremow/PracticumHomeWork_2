package com.example.practicumhomework_2.player.presentation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.App
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.player.domain.PlayerState
import com.example.practicumhomework_2.player.domain.entity.Track
import org.koin.androidx.viewmodel.ext.android.viewModel
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
    private val viewModel  by viewModel<PlayerViewModel>()

    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()
    private lateinit var track: Track
    private lateinit var playButton: ImageView
    private var mainThreadHandler: Handler = Handler(Looper.getMainLooper())
    private var counter = 0
    private val runnable = object : Runnable {
        override fun run() {
            if (playerState == STATE_PLAYING) {
            secondsLeftTextView.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(counter)
            mainThreadHandler.postDelayed(this, DELAY)
            counter += 1000
            }
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
        viewModel.searchTrack(trackId)
        viewModel.liveData.observe(this) {
            when (it) {
                is PlayerState.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                is PlayerState.TrackLoaded -> {
                    track = it.track
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
        }
        playButton.setOnClickListener {
            playbackControl()
        }


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