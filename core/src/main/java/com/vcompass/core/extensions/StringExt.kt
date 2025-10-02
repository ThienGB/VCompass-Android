package com.vcompass.core.extensions

fun String?.ifNull(default: () -> String) = this ?: default.invoke()

fun String.ifEmpty(default: () -> String): String {
    if (this.isNotEmpty()) return this

    return default.invoke()
}