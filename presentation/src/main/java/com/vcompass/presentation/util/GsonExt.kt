package com.vcompass.presentation.util

import com.vcompass.domain.util.tryParseListObject
import com.vcompass.domain.util.tryParseObject


inline fun <reified T> Any?.tryParseListObject(): List<T>? {
    return tryParseListObject<T>()
}

inline fun <reified T> Any?.tryParseObject(): T? {
    return tryParseObject<T>()
}

fun Any?.toJson(): String {
    return toJson()
}