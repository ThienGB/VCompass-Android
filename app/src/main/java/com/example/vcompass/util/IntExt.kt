package com.example.vcompass.util

import com.vcompass.data.model.response.BaseResponse
import java.util.Locale

fun Int.isExpiredToken(): Boolean {
    return this == BaseResponse.ERROR_CODE_UNAUTHORIZED
}

fun Int.isForbidden(): Boolean {
    return this == BaseResponse.ERROR_CODE_FORBIDDEN
}

fun Int.formatThousandK(): String {
    return when {
        this < 1000 -> this.toString()
        this < 1000000 -> String.format(Locale.getDefault(), "%.1fK", this / 1000.0)
        else -> String.format(Locale.getDefault(), "%.1fM", this / 1000000.0)
    }
}