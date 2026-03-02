package com.example.vcompass.helper

import java.text.DecimalFormat

object CommonUtils {
    @JvmStatic
    fun formatCurrency(price: Int?): String {
        if (price == null) return "0"
        val formatter = DecimalFormat("#,###")
        return formatter.format(price.toLong())
    }
}