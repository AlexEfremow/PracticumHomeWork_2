package com.example.practicumhomework_2

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
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
        val trackAdapter = TrackAdapter()

        val searchTrackCallBack = object:Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val trackList = response.body()?.results
                    if (trackList != null) {
                        trackAdapter.updateTrackList(trackList)
                        Log.d("AAA", trackList.toString())
                    }
                }
            }
            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        }
        recyclerView.adapter = trackAdapter

        findViewById<FrameLayout>(R.id.return_button).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.isVisible = p0?.length != 0
                if (p0 != null) {
                    TracksSearchApi.retrofit.searchTracks(p0.toString()).enqueue(searchTrackCallBack)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
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

