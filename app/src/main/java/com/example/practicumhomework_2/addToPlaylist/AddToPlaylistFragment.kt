package com.example.practicumhomework_2.addToPlaylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.databinding.FragmentAddToPlaylistBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddToPlaylistFragment: BottomSheetDialogFragment() {
    private val viewModel by viewModel<AddToPlaylistViewModel>()
    private var _binding : FragmentAddToPlaylistBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  FragmentAddToPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AddToPlaylistAdapter()
        binding.playlistList.adapter = adapter
        viewModel.playlistsLiveData.observe(this) {
            adapter.updatePlaylists(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}