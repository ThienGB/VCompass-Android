package com.example.vcompass.ui.core.bottombar

import com.vcompass.presentation.util.CoreRoute
import com.example.vcompass.R


val TopLevelBottomBar = listOf(
    CoreRoute.HomeGraph,
    CoreRoute.JobLandingGraph,
    CoreRoute.ConversationGraph,
    CoreRoute.ConnectionGraph
)

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
        route = CoreRoute.JobLandingGraph.route,
        label = "",
        unselectedIcon = R.drawable.ic_home_job_fill,
        selectedIcon = R.drawable.ic_home_job_fill
    ),
    BottomBarUIModel(
        route = "",
        label = "",
        unselectedIcon = R.drawable.ic_home_fill,
        selectedIcon = R.drawable.ic_home_fill
    ),
    BottomBarUIModel(
        route = CoreRoute.ConversationGraph.route,
        label = "",
        unselectedIcon = R.drawable.ic_home_conversation_fill,
        selectedIcon = R.drawable.ic_home_conversation_fill
    ),
    BottomBarUIModel(
        route = CoreRoute.ConnectionGraph.route,
        label = "",
        unselectedIcon = R.drawable.ic_home_person_fill,
        selectedIcon = R.drawable.ic_home_person_fill
    ),
)
