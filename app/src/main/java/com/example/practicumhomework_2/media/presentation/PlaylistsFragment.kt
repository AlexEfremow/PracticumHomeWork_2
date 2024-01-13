package com.example.practicumhomework_2.media.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        binding.newPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_mediaFragment_to_playlistCreateFragment)
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Bundle>(
            PlaylistCreateFragment.CREATE_RESULT
        )?.observe(viewLifecycleOwner) {
            val isSuccess = it.getBoolean("isSuccess")
            Log.d("AAA", isSuccess.toString())
            val playlistName = it.getString("playlistName")
            if (isSuccess) Toast.makeText(
                requireContext(),
                "Плейлист $playlistName создан",
                Toast.LENGTH_LONG
            ).show()

        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = PlaylistsFragment()
    }
}