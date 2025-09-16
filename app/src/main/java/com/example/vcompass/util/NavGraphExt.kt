package com.example.vcompass.util

import androidx.navigation.NavController

fun NavController.popBackWithResult(
    key: String,
    value: Any?,
    popUpToRoute: String? = null,
    inclusive: Boolean = false
) {
    previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, value)

    if (popUpToRoute != null) {
        popBackStack(popUpToRoute, inclusive)
    } else {
        popBackStack()
    }
}
