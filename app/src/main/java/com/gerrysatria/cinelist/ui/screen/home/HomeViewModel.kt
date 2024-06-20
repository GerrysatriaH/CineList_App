package com.gerrysatria.cinelist.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gerrysatria.cinelist.data.MovieRepository
import com.gerrysatria.cinelist.model.WatchList
import com.gerrysatria.cinelist.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<WatchList>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<WatchList>>>
        get() = _uiState

    fun getAllMovies() = viewModelScope.launch {
        repository.getAllMovie()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect { movie ->
                _uiState.value = UiState.Success(movie)
            }
        }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun searchMovie(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchMovie(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect { movie ->
                _uiState.value = UiState.Success(movie)
            }
    }
}