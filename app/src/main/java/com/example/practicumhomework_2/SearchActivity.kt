package com.example.practicumhomework_2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.practicumhomework_2.remote.TrackSearchResponse
import com.example.practicumhomework_2.remote.TracksSearchApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private val editText by lazy { findViewById<EditText>(R.id.EditText) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val clearButton = findViewById<ImageView>(R.id.clear_button)
        val noResultsStub = findViewById<LinearLayout>(R.id.no_results_stub)
        val lostConnectionStub = findViewById<LinearLayout>(R.id.lost_connection_stub)
        val refreshButton = findViewById<Button>(R.id.refresh_button)
        val searchHistory = findViewById<ScrollView>(R.id.search_history)
        val historyRecyclerView = findViewById<RecyclerView>(R.id.history_track_list)
        val clearHistoryButton = findViewById<Button>(R.id.clear_history_button)

        val preferences = (application as App).preferences
        val historyAdapter = TrackAdapter { openPlayer(it.trackId) }

        val trackAdapter = TrackAdapter {
            preferences.save(it)
            historyAdapter.updateTrackList(preferences.getTrackList().reversed())
            openPlayer(it.trackId)
        }
        val tracksHistoryList = preferences.getTrackList()

        val textWatcher = TextWatcher {
            clearButton.isVisible = editText.text.isNotEmpty()
            noResultsStub.visibility = View.GONE

            if (editText.hasFocus() && editText.text.isEmpty()) {
                if(preferences.getTrackList().isEmpty()) {
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
        if(tracksHistoryList.isEmpty()) {
            searchHistory.visibility = View.GONE
        } else {
            searchHistory.visibility = View.VISIBLE
        }
        editText.requestFocus()
        editText.addTextChangedListener(textWatcher)
        editText.onFocusChangeListener = FocusListener()

        fun searchTracks(query: String, callback: Callback<TrackSearchResponse>) {
            TracksSearchApi.retrofit.searchTracks(query).enqueue(callback)
        }

        val searchTrackCallBack = object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                if (response.isSuccessful) {
                    lostConnectionStub.visibility = View.GONE
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
                searchHistory.visibility = View.GONE

                lostConnectionStub.visibility = View.VISIBLE
            }

        }
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
    fun openPlayer(trackId: String) {
        val playerIntent = Intent(this, PlayerActivity::class.java).putExtra("track_id", trackId)
        startActivity(playerIntent)
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
}

