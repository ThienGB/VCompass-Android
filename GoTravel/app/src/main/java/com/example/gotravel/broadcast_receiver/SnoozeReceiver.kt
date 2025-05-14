package com.example.gotravel.broadcast_receiver

import NotificationWorker
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Nhắc nhở hoạt động"
        val message = intent.getStringExtra("message") ?: "Hoạt động của bạn sắp bắt đầu trong 5 phút!"
        val scheduleId = intent.getStringExtra("scheduleId") ?: ""

        // Lên lịch thông báo mới sau 10 phút
        val data = Data.Builder()
            .putString("title", title)
            .putString("message", message)
            .putString("scheduleId", scheduleId)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(5, TimeUnit.MINUTES)
            .setInputData(data)
            .addTag("schedule_notifications")
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}