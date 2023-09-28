package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.search.data.local.SearchPreferences
import com.example.practicumhomework_2.search.data.network.TrackSearchResponse
import com.example.practicumhomework_2.search.data.network.TracksSearchApi
import com.example.practicumhomework_2.search.domain.SearchState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(
    private val searchPreferences: SearchPreferences,
    private val tracksSearchApi: TracksSearchApi
) : ViewModel() {

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState

    fun loadTrackList(query: String) {
        tracksSearchApi.searchTracks(query).enqueue(object : Callback<TrackSearchResponse> {
            override fun onResponse(
                call: Call<TrackSearchResponse>,
                response: Response<TrackSearchResponse>
            ) {
                if (response.isSuccessful)
                    _searchState.value =
                        SearchState.Success(response.body()?.results ?: emptyList())
                else {
                    _searchState.value = SearchState.Error(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<TrackSearchResponse>, t: Throwable) {
                _searchState.value = SearchState.Error(t.message ?: "")
            }
        })
    }

    fun saveTrackToHistory(track: Track) {
        searchPreferences.save(track)
    }

    fun getTrackHistory(): List<Track> {
        return searchPreferences.getTrackList().reversed()
    }

    fun clearHistory() {
        searchPreferences.clearHistory()
    }
}