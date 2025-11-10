package com.vcompass.presentation.util

import androidx.compose.runtime.Composable


fun Int.isExpiredToken(): Boolean {
    return this == PresentationConstants.ERROR_CODE_UNAUTHORIZED
}

@Composable
fun Int?.isNotZero(onValue: @Composable () -> String = { "" }): String {
    if (this == null || this == 0) return ""
    return onValue()
}