package com.example.gotravel.helper

import java.text.DecimalFormat

object CommonUtils {
    @JvmStatic
    fun formatCurrency(price: String): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(price.toLong())
    }
}