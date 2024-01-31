package com.example.practicumhomework_2.playlist

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.PlaylistBinding
import com.example.practicumhomework_2.media.presentation.PlaylistsFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistFragment : Fragment() {
    private var _binding: PlaylistBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<PlaylistViewModel>()
    private val behavior by lazy { BottomSheetBehavior.from(binding.bottomView) }
    private val screenHeight by lazy { Resources.getSystem().displayMetrics.heightPixels }
    private val layoutListener = OnGlobalLayoutListener {
        behavior.peekHeight = screenHeight - binding.topView.height
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PlaylistBinding.inflate(inflater, container, false)
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

        viewModel.playlistLiveData.observe(this) {
            binding.playlistName.text = it.name
            binding.playlistDescription.text = it.description
            Glide.with(this)
                .load(it.cover)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.playlistCover)
            binding.tracksTimeMinutes.text = it.totalTime
            binding.playlistTracksCount.text = it.count.toString()
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