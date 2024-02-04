package com.example.practicumhomework_2.playlist.presentation

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.addToPlaylist.presentation.AddToPlaylistFragment
import com.example.practicumhomework_2.databinding.FragmentPlaylistDetailsBinding
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.playlist.presentation.model.DetailedPlaylistModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistDetailsFragment : BottomSheetDialogFragment() {
    private val viewModel by viewModel<PlaylistViewModel>()
    private var _binding: FragmentPlaylistDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playlist = BundleCompat.getParcelable(arguments ?: bundleOf(), ARGS_KEY, DetailedPlaylistModel::class.java)!!

        binding.playlistItemSmall.playlistName.text = playlist.name
        binding.playlistItemSmall.playlistTracksCount.text = requireContext().resources.getQuantityString(R.plurals.tracks_count, playlist.count, playlist.count)
        Glide.with(binding.playlistItemSmall.playlistCover)
            .load(playlist.cover)
            .transform(
                CenterCrop(),
                RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.track_cover_corner_radius))
            )
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.playlistItemSmall.playlistCover)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS_KEY = "args"

        @JvmStatic
        fun newInstance(args: DetailedPlaylistModel): PlaylistDetailsFragment {
            return PlaylistDetailsFragment().apply {
                arguments = bundleOf(ARGS_KEY to args)
            }
        }
    }

}