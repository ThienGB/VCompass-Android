package com.example.gotravel.helper

import android.content.SharedPreferences
import android.net.Uri
import com.example.gotravel.data.model.UserAccount
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object CommonUtils {
    @JvmStatic
    fun formatCurrency(price: String): String {
        if (price.isEmpty()) return "0"
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
    @JvmStatic
    fun upLoadImage(uri: Uri, path: String, onSuccess: (String) -> Unit = {}) {
        val storage = FirebaseStorage.getInstance()
        val imageUrl = uri.toString()
        val storageRef: StorageReference = storage.reference
        val imageRef = storageRef.child("${path}/${System.currentTimeMillis()}.jpg")
        val uploadTask: UploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                onSuccess(uri.toString())
            }
        }.addOnFailureListener { exception ->
            println("Image upload failed: ${exception.message}")
        }
    }
    @JvmStatic
    fun getTimeAgo(createdAt: String?): String {
        if (createdAt.isNullOrEmpty()) return "Không rõ thời gian"

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val createdDate = formatter.parse(createdAt)
            val now = Date()
            val diffMillis = now.time - createdDate.time

            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
            val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
            val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
            val weeks = days / 7
            val months = days / 30
            val years = days / 365

            when {
                years > 0 -> "$years năm trước"
                months > 0 -> "$months tháng trước"
                weeks > 0 -> "$weeks tuần trước"
                days > 0 -> "$days ngày trước"
                hours > 0 -> "$hours giờ trước"
                minutes > 0 -> "$minutes phút trước"
                else -> "Vừa xong"
            }

        } catch (e: Exception) {
            "Không rõ thời gian"
        }
    }
}