package com.example.practicumhomework_2.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.practicumhomework_2.Constants
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.FragmentFavouriteTracksBinding
import com.example.practicumhomework_2.media.domain.FavoriteTracksState
import com.example.practicumhomework_2.search.presentation.TrackAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouriteTracksFragment : Fragment() {
    private val viewModel by viewModel<FavouriteTracksViewModel>()
    private var _binding: FragmentFavouriteTracksBinding? = null
    private val binding get() = _binding!!
    private var isClickAllowed = true
    private var jobDebounce: Job? = null
    private val favoriteTracksAdapter = TrackAdapter(onClick = { openPlayer(it.trackId) })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = favoriteTracksAdapter
        viewModel.favoriteTracksLiveData.observe(this) {
            when (it) {
                is FavoriteTracksState.NotEmpty -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.imageView.visibility = View.GONE
                    binding.textview.visibility = View.GONE
                    favoriteTracksAdapter.updateTrackList(it.trackList)
                }
                else -> {
                    binding.imageView.visibility = View.VISIBLE
                    binding.textview.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openPlayer(trackId: String) {
        if (clickDebounce())
            findNavController().navigate(R.id.playerFragment, bundleOf(Constants.ARG_KEY to trackId))

    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            jobDebounce?.cancel()
            jobDebounce = lifecycleScope.launch {
                delay(TRACK_CLICK_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouriteTracksFragment()
        private const val TRACK_CLICK_DELAY = 1000L
    }
}