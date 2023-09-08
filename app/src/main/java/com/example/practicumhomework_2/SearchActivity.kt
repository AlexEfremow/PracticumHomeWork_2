package com.example.practicumhomework_2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.practicumhomework_2.remote.TrackSearchResponse
import com.example.practicumhomework_2.remote.TracksSearchApi
import kotlinx.coroutines.Runnable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private val preferences by lazy { (application as App).preferences }
    private val mainHandler = Handler(Looper.getMainLooper())

    private val editText by lazy { findViewById<EditText>(R.id.EditText) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }
    private val clearButton by lazy { findViewById<ImageView>(R.id.clear_button) }
    private val noResultsStub by lazy { findViewById<LinearLayout>(R.id.no_results_stub) }
    private val lostConnectionStub by lazy { findViewById<LinearLayout>(R.id.lost_connection_stub) }
    private val refreshButton by lazy { findViewById<Button>(R.id.refresh_button) }
    private val searchHistory by lazy { findViewById<ScrollView>(R.id.search_history) }
    private val historyRecyclerView by lazy { findViewById<RecyclerView>(R.id.history_track_list) }
    private val clearHistoryButton by lazy { findViewById<Button>(R.id.clear_history_button) }
    private val progressBar by lazy { findViewById<FrameLayout>(R.id.progress_bar_layout) }
    private var isClickAllowed = true
    private val runnable = Runnable {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        searchTracks(editText.text.toString(), searchTrackCallBack)
    }


    private val historyAdapter = TrackAdapter { openPlayer(it.trackId) }
    private val trackAdapter = TrackAdapter {
        preferences.save(it)
        historyAdapter.updateTrackList(preferences.getTrackList().reversed())
        openPlayer(it.trackId)
    }
    private val searchTrackCallBack = object : Callback<TrackSearchResponse> {
        override fun onResponse(
            call: Call<TrackSearchResponse>,
            response: Response<TrackSearchResponse>
        ) {
            if (response.isSuccessful) {
                lostConnectionStub.visibility = View.GONE
                progressBar.visibility = View.GONE
                val trackList = response.body()?.results
                if (trackList.isNullOrEmpty()) {
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


        override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
            recyclerView.visibility = View.GONE
            noResultsStub.visibility = View.GONE
            progressBar.visibility = View.GONE
            searchHistory.visibility = View.GONE

            lostConnectionStub.visibility = View.VISIBLE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val tracksHistoryList = preferences.getTrackList()

        val textWatcher = TextWatcher {
            searchDebounce()
            clearButton.isVisible = editText.text.isNotEmpty()
            noResultsStub.visibility = View.GONE

            if (editText.hasFocus() && editText.text.isEmpty()) {
                if (preferences.getTrackList().isEmpty()) {
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

        historyAdapter.updateTrackList(tracksHistoryList.reversed())
        if (tracksHistoryList.isEmpty()) {
            searchHistory.visibility = View.GONE
        } else {
            searchHistory.visibility = View.VISIBLE
        }
        editText.requestFocus()
        editText.addTextChangedListener(textWatcher)
        editText.onFocusChangeListener = FocusListener()



        refreshButton.setOnClickListener {
            searchTracks(editText.text.toString(), searchTrackCallBack)
        }
        recyclerView.adapter = trackAdapter

        findViewById<FrameLayout>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        editText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text != null) {
                    searchTracks(v.text.toString(), searchTrackCallBack)
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } else {
                    noResultsStub.visibility = View.GONE
                    lostConnectionStub.visibility = View.GONE
                }
                true
            } else false
        }
        clearButton.setOnClickListener {
            editText.text.clear()
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        clearHistoryButton.setOnClickListener {
            preferences.clearHistory()
            historyAdapter.updateTrackList(emptyList())
            searchHistory.visibility = View.GONE
        }
    }


    private fun searchTracks(query: String, callback: Callback<TrackSearchResponse>) {
        TracksSearchApi.retrofit.searchTracks(query).enqueue(callback)
    }

    private fun searchDebounce() {
        mainHandler.removeCallbacks(runnable)
        mainHandler.postDelayed(runnable, SEARCH_DELAY)
    }

    private fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, TRACK_CLICK_DELAY)
        }
        return current
    }


    private fun openPlayer(trackId: String) {
        val playerIntent = Intent(this, PlayerActivity::class.java).putExtra("track_id", trackId)
        if (clickDebounce()) {
            startActivity(playerIntent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_key", editText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restore = savedInstanceState.getString("search_key")
        editText.setText(restore)
    }

    companion object {
        private const val SEARCH_DELAY = 2000L
        private const val TRACK_CLICK_DELAY = 1000L
    }
}

