package com.gerrysatria.cinelist.data

import com.gerrysatria.cinelist.model.MovieDataSource
import com.gerrysatria.cinelist.model.WatchList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepository {

    private val watchList = mutableListOf<WatchList>()

    init {
        if (watchList.isEmpty()){
            MovieDataSource.dataMovie.forEach{ movie ->
                watchList.add(WatchList(movie, false))
            }
        }
    }

    fun getAllMovie(): Flow<List<WatchList>> {
        return flowOf(watchList)
    }

    fun getMovieById(movieId: Long): WatchList {
        return watchList.first { data->
            data.movie.id == movieId
        }
    }

    fun searchMovie(query: String): Flow<List<WatchList>> {
        return flowOf(watchList.filter { data->
            data.movie.name.contains(query, ignoreCase = true)
        })
    }

    fun updateWatchlist(movieId: Long, newStatusValue: Boolean): Flow<Boolean> {
        val index = watchList.indexOfFirst { data->
            data.movie.id == movieId
        }
        val result = if (index >= 0) {
            val watchLists = watchList[index]
            watchList[index] = watchLists.copy(
                movie = watchLists.movie,
                status = newStatusValue
            )
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedWatchlist(): Flow<List<WatchList>> {
        return getAllMovie()
            .map { movie ->
                movie.filter { watchlist ->
                    watchlist.status
                }
            }
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(): MovieRepository =
            instance ?: synchronized(this) {
                MovieRepository().apply {
                    instance = this
                }
            }
    }
}