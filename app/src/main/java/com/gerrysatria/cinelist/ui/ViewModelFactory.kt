package com.gerrysatria.cinelist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gerrysatria.cinelist.data.MovieRepository
import com.gerrysatria.cinelist.ui.screen.detail.DetailViewModel
import com.gerrysatria.cinelist.ui.screen.home.HomeViewModel
import com.gerrysatria.cinelist.ui.screen.watchlist.WatchlistViewModel

class ViewModelFactory(
    private val repository: MovieRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)){
            return WatchlistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}