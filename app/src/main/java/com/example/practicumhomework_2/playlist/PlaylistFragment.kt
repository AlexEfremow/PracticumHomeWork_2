package com.example.practicumhomework_2.playlist

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.PlaylistBinding
import com.example.practicumhomework_2.media.presentation.PlaylistsFragment
import com.example.practicumhomework_2.search.presentation.TrackAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {
    private var _binding: PlaylistBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<PlaylistViewModel>()
    private var behavior: BottomSheetBehavior<LinearLayout>? = null
    private val screenHeight by lazy { Resources.getSystem().displayMetrics.heightPixels }
    private val layoutListener = OnGlobalLayoutListener {
        behavior?.peekHeight = screenHeight - binding.topView.height
        Log.d("AAA Screen Height", screenHeight.toString())
        Log.d("AAA bottom Height", binding.topView.height.toString())
    }
    private val trackAdapter =
        TrackAdapter(
            onClick = { findNavController().navigate(R.id.playerFragment, bundleOf("track_id" to it.trackId)) },
            onLongClick = {
                Toast.makeText(requireContext(), "LONG CLICK", Toast.LENGTH_LONG).show()
            })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistBinding.inflate(inflater, container, false)
        behavior = BottomSheetBehavior.from(binding.bottomView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playlistId = arguments?.getInt(PlaylistsFragment.PLAYLIST_ID_KEY)
        if (playlistId == null) Toast.makeText(
            requireContext(),
            "Empty Playlist Id",
            Toast.LENGTH_LONG
        ).show() else viewModel.getPlaylistById(playlistId)
        binding.playlistList.adapter = trackAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playlistFlow?.collect {
                    trackAdapter.updateTrackList(it.trackList)
                    binding.playlistName.text = it.name
                    binding.playlistDescription.text = it.description
                    Glide.with(this@PlaylistFragment)
                        .load(it.cover)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(binding.playlistCover)
                    binding.tracksTimeMinutes.text = requireContext().resources.getQuantityString(
                        R.plurals.minutes_count,
                        it.totalTime,
                        it.totalTime
                    )
                    binding.playlistTracksCount.text = requireContext().resources.getQuantityString(
                        R.plurals.tracks_count,
                        it.count,
                        it.count
                    )
                }
            }
        }

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }

    override fun onPause() {
        super.onPause()
        binding.root.viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}