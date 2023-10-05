package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.search.domain.TrackListSearchCallBack
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchState

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState> = _searchState

    fun loadTrackList(query: String) {
        val callBack = object : TrackListSearchCallBack {

            override fun onSuccess(data: List<Track>) {
                _searchState.value = SearchState.Success(data)
            }

            override fun onError(message: String) {
                _searchState.value = SearchState.Error(message)
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