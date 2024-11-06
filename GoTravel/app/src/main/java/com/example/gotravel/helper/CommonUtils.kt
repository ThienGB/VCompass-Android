package com.example.gotravel.helper

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object CommonUtils {
    @JvmStatic
    fun formatCurrency(price: String): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(price.toLong())
    }
    @JvmStatic
    fun formatDate(millis: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(millis)
        return dateFormat.format(date)
    }
}