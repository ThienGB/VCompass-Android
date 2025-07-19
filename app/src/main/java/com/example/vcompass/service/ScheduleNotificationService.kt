package com.example.vcompass.service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.vcompass.R
import com.example.vcompass.data.api.model.Schedule
import com.example.vcompass.ui.module.user.main.MainUserActivity

class ScheduleNotificationService : Service() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()

        val notificationIntent = Intent(this, MainUserActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
            .setContentTitle("Lịch trình đang diễn ra")
            .setContentText("Lịch trình đang được theo dõi, bạn sẽ nhận được thông báo về hoạt động sắp tới")
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentIntent(pendingIntent)
            .setOngoing(true) // Thông báo không thể xóa
            .build()

        // Khởi động Foreground Service
        startForeground(SERVICE_NOTIFICATION_ID, notification)
//        val schedule = fetchScheduleById(scheduleId)
//        schedule?.let { scheduleNotifications(it, this) }

        return START_STICKY // Dịch vụ sẽ khởi động lại nếu bị kill
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            SERVICE_CHANNEL_ID,
            "Dịch vụ lịch trình",
            NotificationManager.IMPORTANCE_LOW // Không làm phiền người dùng
        ).apply {
            description = "Kênh cho dịch vụ chạy nền"
        }
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun fetchScheduleById(scheduleId: String): Schedule? {
        // Thay thế bằng logic lấy Schedule từ API hoặc local storage
        // Ví dụ: Truy vấn từ ViewModel hoặc database
        return null
    }

    companion object {
        const val SERVICE_CHANNEL_ID = "schedule_service_channel"
        const val SERVICE_NOTIFICATION_ID = 1001
    }
}