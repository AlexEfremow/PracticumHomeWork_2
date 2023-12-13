package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val _searchLiveData = MutableLiveData<SearchState>()
    val searchLiveData: LiveData<SearchState> = _searchLiveData
    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    fun loadTrackList(query: String) {
        if (query.isEmpty()) return
        viewModelScope.launch {
            _searchLiveData.value = SearchState.Loading()
            _searchLiveData.value = interactor.searchTracks(query)
        }
    }
    fun searchDebounce(changedText: String) {
        if(latestSearchText == changedText) return
        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY)
            loadTrackList(changedText)
        }
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
    companion object {
        private const val SEARCH_DELAY = 500L
    }
}