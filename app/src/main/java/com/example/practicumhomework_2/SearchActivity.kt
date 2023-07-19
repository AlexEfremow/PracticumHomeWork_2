package com.example.practicumhomework_2

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

        val trackAdapter = TrackAdapter()

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
                lostConnectionStub.visibility = View.VISIBLE
                refreshButton.setOnClickListener {
                    searchTracks(editText.text.toString(), this)
                }
            }
        }
        recyclerView.adapter = trackAdapter

        findViewById<FrameLayout>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.isVisible = p0?.length != 0
                if (p0 != null) {
                    searchTracks(p0.toString(), searchTrackCallBack)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }
        editText.addTextChangedListener(textWatcher)
        clearButton.setOnClickListener {
            editText.text.clear()
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
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
}

