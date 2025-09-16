package com.example.vcompass.util

import com.example.vcompass.BuildConfig

fun String.ifDebug(onValue: () -> String): String {
    if (BuildConfig.DEBUG)
        return onValue.invoke()

    return this
}