package com.vcompass.presentation.util

import androidx.compose.runtime.Composable
import java.util.Locale


fun Int.isExpiredToken(): Boolean {
    return this == PresentationConstants.ERROR_CODE_UNAUTHORIZED
}

@Composable
fun Int?.isNotZero(onValue: @Composable () -> String = { "" }): String {
    if (this == null || this == 0) return ""
    return onValue()
}

fun Int.formatThousandK(): String {
    return when {
        this < 1000 -> this.toString()
        this < 1000000 -> String.format(Locale.getDefault(), "%.1fK", this / 1000.0)
        else -> String.format(Locale.getDefault(), "%.1fM", this / 1000000.0)
    }
}