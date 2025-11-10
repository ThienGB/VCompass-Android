package com.example.vcompass.util

import java.util.Locale

fun Int.formatThousandK(): String {
    return when {
        this < 1000 -> this.toString()
        this < 1000000 -> String.format(Locale.getDefault(), "%.1fK", this / 1000.0)
        else -> String.format(Locale.getDefault(), "%.1fM", this / 1000000.0)
    }
}