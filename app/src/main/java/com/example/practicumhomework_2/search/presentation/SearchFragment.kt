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
import com.example.practicumhomework_2.player.presentation.PlayerActivity
import com.example.practicumhomework_2.search.domain.SearchState
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment: Fragment() {
    private val mainHandler = Handler(Looper.getMainLooper())
    private val viewModel  by viewModel<SearchViewModel>()

    private val editText by lazy { view!!.findViewById<EditText>(R.id.EditText) }
    private val recyclerView by lazy { view!!.findViewById<RecyclerView>(R.id.recycler_view) }
    private val clearButton by lazy { view!!.findViewById<ImageView>(R.id.clear_button) }
    private val noResultsStub by lazy { view!!.findViewById<LinearLayout>(R.id.no_results_stub) }
    private val lostConnectionStub by lazy { view!!.findViewById<LinearLayout>(R.id.lost_connection_stub) }
    private val refreshButton by lazy { view!!.findViewById<Button>(R.id.refresh_button) }
    private val searchHistory by lazy { view!!.findViewById<ScrollView>(R.id.search_history) }
    private val historyRecyclerView by lazy { view!!.findViewById<RecyclerView>(R.id.history_track_list) }
    private val clearHistoryButton by lazy { view!!.findViewById<Button>(R.id.clear_history_button) }
    private val progressBar by lazy { view!!.findViewById<FrameLayout>(R.id.progress_bar_layout) }
    private var isClickAllowed = true
    private val runnable = kotlinx.coroutines.Runnable {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        searchTracks(editText.text.toString())
    }


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
    ): View? {
        return inflater.inflate(R.layout.search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tracksHistoryList = viewModel.getTrackHistory()

        viewModel.searchState.observe(this) {
            when (it) {
                is SearchState.Error -> {
                    recyclerView.visibility = View.GONE
                    noResultsStub.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    searchHistory.visibility = View.GONE
                    lostConnectionStub.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is SearchState.Success -> {
                    lostConnectionStub.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    val trackList = it.trackList
                    if (trackList.isEmpty()) {
                        noResultsStub.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        searchHistory.visibility = View.GONE
                    } else {
                        noResultsStub.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        trackAdapter.updateTrackList(trackList)
                    }
                }
            }

        }
        val textWatcher = TextWatcher {
            searchDebounce()
            clearButton.isVisible = editText.text.isNotEmpty()
            noResultsStub.visibility = View.GONE

            if (editText.hasFocus() && editText.text.isEmpty()) {
                if (viewModel.getTrackHistory().isEmpty()) {
                    searchHistory.visibility = View.GONE
                } else {
                    searchHistory.visibility = View.VISIBLE
                }
                recyclerView.visibility = View.GONE
                lostConnectionStub.visibility = View.GONE
                noResultsStub.visibility = View.GONE
            } else {
                searchHistory.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        historyRecyclerView.adapter = historyAdapter

        historyAdapter.updateTrackList(tracksHistoryList)
        if (tracksHistoryList.isEmpty()) {
            searchHistory.visibility = View.GONE
        } else {
            searchHistory.visibility = View.VISIBLE
        }
        editText.requestFocus()
        editText.addTextChangedListener(textWatcher)
        editText.onFocusChangeListener = FocusListener()



        refreshButton.setOnClickListener {
            searchTracks(editText.text.toString())
        }
        recyclerView.adapter = trackAdapter

        view.findViewById<FrameLayout>(R.id.return_button).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        editText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text != null) {
                    searchTracks(v.text.toString())
                    inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
                } else {
                    noResultsStub.visibility = View.GONE
                    lostConnectionStub.visibility = View.GONE
                }
                true
            } else false
        }
        clearButton.setOnClickListener {
            editText.text.clear()
            inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        }
        clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
            historyAdapter.updateTrackList(emptyList())
            searchHistory.visibility = View.GONE
        }
    }
    private fun searchTracks(query: String) {
        viewModel.loadTrackList(query)
    }

    private fun searchDebounce() {
        mainHandler.removeCallbacks(runnable)
        mainHandler.postDelayed(runnable, SEARCH_DELAY)
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
        val playerIntent = Intent(requireActivity(), PlayerActivity::class.java).putExtra("track_id", trackId)
        if (clickDebounce()) {
            startActivity(playerIntent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_key", editText.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val restore = savedInstanceState?.getString("search_key")
        editText.setText(restore)
    }

    companion object {
        private const val SEARCH_DELAY = 2000L
        private const val TRACK_CLICK_DELAY = 1000L
    }
}