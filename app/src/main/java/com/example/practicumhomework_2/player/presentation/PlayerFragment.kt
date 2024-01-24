package com.example.practicumhomework_2.player.presentation

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.addToPlaylist.presentation.AddToPlaylistFragment
import com.example.practicumhomework_2.databinding.AudioPlayerBinding
import com.example.practicumhomework_2.player.domain.PlayerState
import com.example.practicumhomework_2.player.domain.entity.Track
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerFragment : Fragment() {

    private var _binding: AudioPlayerBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<PlayerViewModel>()

    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()
    private lateinit var track: Track

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AudioPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trackId = arguments?.getString("track_id") ?: ""
        viewModel.searchTrack(trackId)
        viewModel.trackLiveData.observe(this) {
            when (it) {
                is PlayerState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is PlayerState.TrackLoaded -> {
                    track = it.track
                    binding.trackName.text = track.trackName
                    binding.artistName.text = track.artistName
                    binding.trackDurationValue.text = track.timeFormat()
                    binding.albumValue.text = track.collectionName
                    preparePlayer()

                    binding.trackReleaseYearValue.text = track.releaseDate.take(4)
                    binding.trackGenreValue.text = track.primaryGenreName
                    binding.trackCountryValue.text = track.country


                    Glide.with(requireContext())
                        .load(track.getCoverArtwork())
                        .transform(
                            CenterInside(),
                            RoundedCorners(resources.getDimensionPixelSize(R.dimen.track_poster_corner_radius))
                        )
                        .placeholder(R.drawable.placeholder)
                        .into(binding.trackPoster)
                }
                is PlayerState.InProgress -> {
                    binding.trackTime.text = it.counterText
                }
                is PlayerState.Initial -> {}
            }
        }


        binding.playButton.setOnClickListener {
            playbackControl()
        }
        binding.addSongButton.setOnClickListener {
            AddToPlaylistFragment
                .newInstance(track)
                .show(parentFragmentManager, null)
        }



        binding.returnButton.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.isFavoriteLiveData.observe(this) {
            binding.addFavouriteSong.setImageResource(if (it) R.drawable.favorite_button else R.drawable.add_favourite_song)
        }
        binding.addFavouriteSong.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
    }

    fun preparePlayer() {
        if (playerState == STATE_DEFAULT) {
            mediaPlayer.setDataSource(track.previewUrl)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                binding.playButton.isEnabled = true
                playerState = STATE_PREPARED
            }
            mediaPlayer.setOnCompletionListener {
                binding.playButton.setImageResource(R.drawable.ic_baseline_play_circle_24)
                playerState = STATE_PREPARED
                viewModel.stopProgress()
                viewModel.resetCounter()
            }
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        binding.playButton.setImageResource(R.drawable.pause_button)
        viewModel.startProgress(mediaPlayer)
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        binding.playButton.setImageResource(R.drawable.ic_baseline_play_circle_24)
        viewModel.stopProgress()
        playerState = STATE_PAUSED
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
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
    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}