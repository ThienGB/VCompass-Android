package com.example.vcompass.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.formatDateToDayMonth(): String {
    if (isNullOrEmpty()) return ""
    return try {
        val input = LocalDate.parse(this)
        input.format(DateTimeFormatter.ofPattern("dd/MM"))
    } catch (e: Exception) {
        ""
    }
}