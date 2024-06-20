package com.gerrysatria.cinelist.ui.screen.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerrysatria.cinelist.data.MovieRepository
import com.gerrysatria.cinelist.model.WatchList
import com.gerrysatria.cinelist.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WatchlistViewModel(
    private val repository: MovieRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<WatchList>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<WatchList>>>
        get() = _uiState

    fun getAddedWatchlist() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedWatchlist()
                .collect { movie ->
                    _uiState.value = UiState.Success(movie)
                }
        }
    }
}