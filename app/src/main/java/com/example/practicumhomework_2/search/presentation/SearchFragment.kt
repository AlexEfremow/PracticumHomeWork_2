package com.example.practicumhomework_2.search.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.practicumhomework_2.R
import com.example.practicumhomework_2.databinding.SearchBinding
import com.example.practicumhomework_2.player.presentation.PlayerActivity
import com.example.practicumhomework_2.search.domain.SearchState
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val mainHandler = Handler(Looper.getMainLooper())
    private val viewModel by viewModel<SearchViewModel>()
    private var _binding: SearchBinding? = null
    private val binding get() = _binding!!
    private var isClickAllowed = true


    private val historyAdapter = TrackAdapter { openPlayer(it.trackId) }
    private val trackAdapter = TrackAdapter {
        viewModel.saveTrackToHistory(it)
        historyAdapter.updateTrackList(viewModel.getTrackHistory())
        openPlayer(it.trackId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tracksHistoryList = viewModel.getTrackHistory()
        binding.recyclerView.isVisible = binding.EditText.text.isNotEmpty()

        viewModel.searchState.observe(this) {
            when (it) {
                is SearchState.Error -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.noResultsStub.root.visibility = View.GONE
                    binding.progressBarLayout.root.visibility = View.GONE
                    binding.searchHistory.root.visibility = View.GONE
                    binding.lostConnectionStub.root.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is SearchState.Success -> {
                    binding.lostConnectionStub.root.visibility = View.GONE
                    binding.progressBarLayout.root.visibility = View.GONE
                    val trackList = it.trackList
                    if (trackList.isEmpty()) {
                        binding.noResultsStub.root.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.searchHistory.root.visibility = View.GONE
                    } else {
                        binding.noResultsStub.root.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        trackAdapter.updateTrackList(trackList)
                    }
                }
                is SearchState.Loading -> {
                    binding.progressBarLayout.root.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }

        }
        val textWatcher = TextWatcher {
            binding.clearButton.isVisible = binding.EditText.text.isNotEmpty()
            binding.recyclerView.isVisible = binding.EditText.text.isNotEmpty()
            binding.noResultsStub.root.visibility = View.GONE
            viewModel.searchDebounce(it)

            if (binding.EditText.hasFocus() && binding.EditText.text.isEmpty()) {
                if (viewModel.getTrackHistory().isEmpty()) {
                    binding.searchHistory.root.visibility = View.GONE
                } else {
                    binding.searchHistory.root.visibility = View.VISIBLE
                }
                binding.recyclerView.visibility = View.GONE
                binding.lostConnectionStub.root.visibility = View.GONE
                binding.noResultsStub.root.visibility = View.GONE
            } else {
                binding.searchHistory.root.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        }

        binding.searchHistory.historyTrackList.adapter = historyAdapter

        historyAdapter.updateTrackList(tracksHistoryList)
        if (tracksHistoryList.isEmpty()) {
            binding.searchHistory.root.visibility = View.GONE
        } else {
            binding.searchHistory.root.visibility = View.VISIBLE
        }
        binding.EditText.requestFocus()
        binding.EditText.addTextChangedListener(textWatcher)
//        binding.EditText.onFocusChangeListener = FocusListener()


        binding.lostConnectionStub.refreshButton.setOnClickListener {
            searchTracks(binding.EditText.text.toString())
        }
        binding.recyclerView.adapter = trackAdapter

        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.EditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text != null) {
                    searchTracks(v.text.toString())
                    inputMethodManager.hideSoftInputFromWindow(
                        requireActivity().currentFocus?.windowToken,
                        0
                    )
                } else {
                    binding.noResultsStub.root.visibility = View.GONE
                    binding.lostConnectionStub.root.visibility = View.GONE
                }
                true
            } else false
        }
        binding.clearButton.setOnClickListener {
            binding.EditText.text?.clear()
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus?.windowToken,
                0
            )
        }
        binding.searchHistory.clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
            historyAdapter.updateTrackList(emptyList())
            binding.searchHistory.root.visibility = View.GONE
        }
    }

    private fun searchTracks(query: String) {
        viewModel.loadTrackList(query)
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, TRACK_CLICK_DELAY)
        }
        return current
    }


    private fun openPlayer(trackId: String) {
        val playerIntent =
            Intent(requireActivity(), PlayerActivity::class.java).putExtra("track_id", trackId)
        if (clickDebounce()) {
            startActivity(playerIntent)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TRACK_CLICK_DELAY = 1000L
    }
}