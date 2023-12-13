package com.example.practicumhomework_2.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicumhomework_2.player.domain.entity.Track
import com.example.practicumhomework_2.search.domain.SearchInteractor
import com.example.practicumhomework_2.search.domain.SearchState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val interactor: SearchInteractor) : ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()
    private var latestSearchText: String? = null
    private var searchJob: Job? = null

    fun loadTrackList(query: String) {
        if (query.isEmpty()) return
        viewModelScope.launch {
            _searchState.value = SearchState.Loading()
            _searchState.value = interactor.searchTracks(query)
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