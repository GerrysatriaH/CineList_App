package com.gerrysatria.cinelist.di

import com.gerrysatria.cinelist.data.MovieRepository

object Injection {
    fun provideRepository(): MovieRepository {
        return MovieRepository.getInstance()
    }
}