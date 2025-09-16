package com.vcompass.data.util

import com.google.gson.Gson

fun Any?.tryParseToInt(): Int? {
    if (this is Int) return this
    val gson = Gson()
    val jsonElement = gson.toJsonTree(this)

    return try {
        val number = gson.fromJson(jsonElement, Int::class.java)
        number
    } catch (e: Exception) {
        null
    }
}