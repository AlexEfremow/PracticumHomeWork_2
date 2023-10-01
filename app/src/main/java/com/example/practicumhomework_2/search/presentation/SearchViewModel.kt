package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.search.data.network.TrackSearchResponse
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState

    fun loadTrackList(query: String) {
        val callBack = object : Callback<TrackSearchResponse> {
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
        }
        interactor.searchTracks(query, callBack)
    }

    fun saveTrackToHistory(track: Track) {
        interactor.saveTrackToHistory(track)
    }

    fun getTrackHistory(): List<Track> {
        return interactor.getTrackHistory()
    }

    fun clearHistory() {
        interactor.clearHistory()
    }
}