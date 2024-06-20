package com.gerrysatria.cinelist.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Watchlist : Screen("watchlist")
    object About : Screen("about")
    object DetailMovie : Screen("home/{movieId}") {
        fun createRoute(movieId: Long) = "home/$movieId"
    }
}