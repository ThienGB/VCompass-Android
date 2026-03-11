package com.vcompass.presentation.util

sealed class CoreRoute(val route: String) {
    data object MainGraph : CoreRoute("main")
    data object Splash : CoreRoute("splash")
    data object Introduce : CoreRoute("introduce")
    data object Login : CoreRoute("login")
    data object Register : CoreRoute("register")
    data object Forgot : CoreRoute("forgot")

    data object Home : CoreRoute("home")
    data object HomeFeed : CoreRoute("home_feed")
    data object Explore : CoreRoute("explore")
    data object ExploreSearch : CoreRoute("explore_search")
    data object MapSearch : CoreRoute("map_search")
    data object Schedule : CoreRoute("schedule")
    data object CreateSchedule : CoreRoute("create_schedule")
    data object Notification : CoreRoute("notification")
    data object Conversation : CoreRoute("conversation")
    data object ChatDetail : CoreRoute("chat_detail")
    data object AccommodationDetail : CoreRoute("accommodation_detail")
}