package com.example.vcompass.ui.core.bottombar

import com.vcompass.presentation.util.CoreRoute
import com.example.vcompass.R


data class BottomBarUIModel(
    val route: String,
    val label: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

val bottomDestinations = listOf(
    BottomBarUIModel(
        route = CoreRoute.HomeGraph.route,
        label = "",
        unselectedIcon = R.drawable.ic_home_fill,
        selectedIcon = R.drawable.ic_home_fill
    ),
    BottomBarUIModel(
        route = CoreRoute.ExploreGraph.route,
        label = "",
        unselectedIcon = R.drawable.ic_explore_filled_24dp,
        selectedIcon = R.drawable.ic_explore_filled_24dp
    ),
    BottomBarUIModel(
        route = "",
        label = "",
        unselectedIcon = R.drawable.ic_home_fill,
        selectedIcon = R.drawable.ic_home_fill
    ),
    BottomBarUIModel(
        route = CoreRoute.HomeSearchGraph.route,
        label = "",
        unselectedIcon = R.drawable.ic_travel_search_24dp,
        selectedIcon = R.drawable.ic_travel_search_24dp
    ),
    BottomBarUIModel(
        route = CoreRoute.ConnectionGraph.route,
        label = "",
        unselectedIcon = R.drawable.ic_home_person_fill,
        selectedIcon = R.drawable.ic_home_person_fill
    ),
)
