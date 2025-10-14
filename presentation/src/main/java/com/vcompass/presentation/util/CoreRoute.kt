package com.vcompass.presentation.util

sealed class CoreRoute(val route: String) {
    data object Splash : CoreRoute("splash")
    data object Introduce : CoreRoute("introduce")
    data object Login : CoreRoute("login")
    data object Home : CoreRoute("home")

    data object HomeGraph : CoreRoute("home_graph")
    data object ExploreGraph : CoreRoute("explore_graph")
    data object ExploreSearchGraph : CoreRoute("explore_search_graph")
    data object HomeSearchGraph : CoreRoute("home_search_graph")
    data object ConnectionGraph : CoreRoute("connection_graph")
}