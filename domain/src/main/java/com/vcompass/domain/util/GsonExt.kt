package com.vcompass.domain.util

import com.vcompass.data.util.tryParseListObject
import com.vcompass.data.util.tryParseObject

inline fun <reified T> Any?.tryParseListObject(): List<T>? {
    return tryParseListObject<T>()
}

inline fun <reified T> Any?.tryParseObject(): T? {
    return tryParseObject<T>()
}

fun Any?.toJson(): String {
    return toJson()
}