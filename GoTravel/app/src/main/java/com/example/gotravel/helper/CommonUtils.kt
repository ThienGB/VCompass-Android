package com.example.gotravel.helper

import android.content.SharedPreferences
import com.example.gotravel.data.model.UserAccount
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
    @JvmStatic
    fun formatDateHaveDay(timeMillis: Long): String {
        val dayFormat = SimpleDateFormat("EEEE, dd/MM/yyyy", Locale("vi", "VN"))
        dayFormat.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh")

        val date = Date(timeMillis)
        return dayFormat.format(date)
    }
    @JvmStatic
    fun getUserFromShareRef(sharedPreferences: SharedPreferences): UserAccount {
        return UserAccount(
            sharedPreferences.getString("userId", "").toString(),
            sharedPreferences.getString("fullname", "").toString(),
            sharedPreferences.getString("email", "").toString(),
            sharedPreferences.getString("phone", "").toString(),
            sharedPreferences.getString("role", "").toString())
    }
}