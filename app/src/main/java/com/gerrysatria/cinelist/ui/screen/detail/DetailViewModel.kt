package com.gerrysatria.cinelist.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerrysatria.cinelist.data.MovieRepository
import com.gerrysatria.cinelist.model.Movie
import com.gerrysatria.cinelist.model.WatchList
import com.gerrysatria.cinelist.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<WatchList>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<WatchList>>
        get() = _uiState

    fun getMovieById(movieId: Long) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getMovieById(movieId))
    }

    fun addToWatchlist(movie: Movie, status: Boolean) = viewModelScope.launch {
        repository.updateWatchlist(movie.id, status)
    }
}