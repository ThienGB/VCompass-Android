package com.example.vcompass.util

import androidx.navigation.NavHostController
import org.koin.mp.KoinPlatform.getKoin

object ScreenContext {
    const val SCOPE_ID = "MAIN_SCOPE"

    fun scope() = getKoin().getScope(SCOPE_ID)

    val navController: NavHostController
        get() = scope().get()
}