package com.example.vcompass.helper

import android.content.SharedPreferences
import android.net.Uri
import com.example.vcompass.data.model.UserAccount
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object CommonUtils {
    @JvmStatic
    fun formatCurrency(price: Int?): String {
        if (price == null) return "0"
        val formatter = DecimalFormat("#,###")
        return formatter.format(price.toLong())
    }
    @JvmStatic
    fun formatDate(millis: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(millis)
        return dateFormat.format(date)
    }

    @JvmStatic
    fun getUserFromShareRef(sharedPreferences: SharedPreferences): UserAccount {
        return UserAccount(
            sharedPreferences.getString("userId", "").toString(),
            sharedPreferences.getString("fullName", "").toString(),
            sharedPreferences.getString("email", "").toString(),
            sharedPreferences.getString("role", "").toString(),
            sharedPreferences.getString("phone", "").toString(),
            sharedPreferences.getString("status", "").toString(),
            sharedPreferences.getString("image", "").toString())
    }
    @JvmStatic
    fun formatDateToVi(date: Date?): String {
        if (date == null) return ""
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")
        return sdf.format(date)
    }
}