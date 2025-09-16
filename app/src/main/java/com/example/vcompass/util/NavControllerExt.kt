package com.example.vcompass.util

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.vcompass.presentation.state.NavigateState

fun NavController.navigateWithState(
    navigateState: NavigateState
) {
    if (navigateState is NavigateState.AllowNavigate) {
        val route = navigateState.route
        when {
            navigateState.isClearStack -> clearAllStackAndAdd(route)
            navigateState.isReplace -> replace(route)
            else -> add(route)
        }
    }
}

fun NavController.clearAllStackAndAdd(route: String) {
    navigate(route) {
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.popToTargetAndAdd(route: String, targetBack: String) {
    navigate(route) {
        popUpTo(targetBack) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.goBack(target: String? = null) {
    target?.let {
        popBackStack(it, inclusive = true)
    } ?: run {
        popBackStack()
    }
}

fun NavController.add(route: String) {
    val popped = this.popBackStack(route, inclusive = false)
    if (!popped) {
        this.navigate(route)
    }
}

fun NavController.replace(route: String) {
    val currentRoute = this.currentDestination?.route
    navigate(route) {
        currentRoute?.let { popUpTo(it) { inclusive = true } }
        launchSingleTop = true
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
