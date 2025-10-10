package com.example.vcompass.util

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.vcompass.presentation.state.NavigateState
import kotlinx.coroutines.flow.StateFlow

fun NavController.goWithState(
    navigateState: NavigateState
) {
    if (navigateState is NavigateState.AllowNavigate) {
        val route = navigateState.route
        when {
            navigateState.isClearStack -> clearAllStackAndGo(route)
            navigateState.isReplace -> replace(route)
            else -> add(route)
        }
    }
}

fun NavController.clearAllStackAndGo(route: String) {
    navigate(route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
        restoreState = false
    }
}

fun NavController.backAndNavigate(navigate: String, targetBack: String) {
    popBackStack(targetBack, inclusive = false)
    navigate(navigate) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.back(target: String? = null) {
    target?.let {
        popBackStack(it, inclusive = false)
    } ?: run {
        popBackStack()
    }
}

fun NavController.add(route: String) {
    navigate(route) {
        // avoid duplicate screen
        launchSingleTop = false
        restoreState = true
    }
}

fun NavController.replace(route: String) {
    val currentRoute = this.currentDestination?.route
    navigate(route) {
        currentRoute?.let { popUpTo(it) { inclusive = true } }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavDestination?.isTopLevelDestinationOf(route: String): Boolean {
    return this?.hierarchy?.any { it.route == route } == true
}

fun NavController.navigateToTopLevel(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

inline fun <reified T> NavController.setArg(key: String, value: T) {
    currentBackStackEntry?.savedStateHandle?.set(key, value)
}

inline fun <reified T> NavController.getArg(key: String): T? {
    return previousBackStackEntry?.savedStateHandle?.get<T>(key)
}

inline fun <reified T> NavController.getArgFlow(key: String): StateFlow<T?>? {
    return previousBackStackEntry?.savedStateHandle?.getStateFlow<T?>(key, null)
}

fun NavController.clearArg(key: String) {
    previousBackStackEntry?.savedStateHandle?.remove<Any>(key)
}