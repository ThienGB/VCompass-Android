package com.example.vcompass.service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.vcompass.domain.model.response.schedule.ScheduleActivity
import com.example.vcompass.R

class ScheduleNotificationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this, ScheduleActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
            .setContentTitle("Lịch trình đang diễn ra")
            .setContentText("Lịch trình đang được theo dõi, bạn sẽ nhận được thông báo về hoạt động sắp tới")
            .setSmallIcon(R.drawable.logo_no_cap)
            .setContentIntent(pendingIntent)
            .setOngoing(true) // Thông báo không thể xóa
            .build()

        startForeground(SERVICE_NOTIFICATION_ID, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

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

    companion object {
        const val SERVICE_CHANNEL_ID = "schedule_service_channel"
        const val SERVICE_NOTIFICATION_ID = 1001
    }
}