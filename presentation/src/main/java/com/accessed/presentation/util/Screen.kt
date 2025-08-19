package com.accessed.presentation.util

sealed class Screen(val route: String) {
    object Splash : Screen("splash")

    object Introduce : Screen("introduce")
    object Login : Screen("login")
    object Home : Screen("home")
}