package com.example.practicumhomework_2.media.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.createPlaylist.presentation.PlaylistCreateFragment
import com.example.practicumhomework_2.databinding.FragmentPlaylistsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {
    private val viewModel by viewModel<PlaylistsViewModel>()
    private var _binding: FragmentPlaylistsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PlaylistAdapter()
        binding.playlistList.adapter = adapter
        viewModel.playlistsLiveData.observe(this) {
            binding.playlistList.isVisible = it.isNotEmpty()
            binding.noPlaylistsStub.root.isVisible = it.isEmpty()
            adapter.updatePlaylists(it)
        }
        binding.newPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_mediaFragment_to_playlistCreateFragment)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = PlaylistsFragment()
    }
}