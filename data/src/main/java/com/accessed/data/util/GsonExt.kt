package com.accessed.data.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken


inline fun <reified T> Any?.tryParseListObject(): List<T>? {
    val gSon = GsonBuilder().create()
    return try {
        val jsonElement = gSon.toJson(this)
        gSon.fromJson(jsonElement, object : TypeToken<List<T>>() {}.type)
    } catch (e: JsonParseException) {
        try {
            val jsonElement = gSon.toJson(this.toString())
            gSon.fromJson(jsonElement, object : TypeToken<List<T>>() {}.type)
        } catch (e: Exception) {
            null
        }
    }
}

inline fun <reified T> Any?.tryParseObject(): T? {
    val gSon = GsonBuilder().create()
    return try {
        gSon.fromJson(this.toString(), T::class.java)
    } catch (e: Exception) {
        try {
            gSon.fromJson(this.toJson(), T::class.java)
        } catch (e: JsonParseException) {
            null
        }
    }
}

fun Any?.toJson(): String {
    val gSon = GsonBuilder().create()
    return gSon.toJson(this)
}