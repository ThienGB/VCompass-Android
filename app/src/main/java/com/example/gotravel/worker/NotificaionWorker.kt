import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.gotravel.R
import com.example.gotravel.broadcast_receiver.CompleteReceiver
import com.example.gotravel.broadcast_receiver.SnoozeReceiver
import com.example.gotravel.ui.module.user.main.MainUserActivity
import java.util.*
import androidx.core.net.toUri

class NotificationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: "Nhắc nhở hoạt động"
        val message = inputData.getString("message") ?: "Hoạt động của bạn sắp bắt đầu trong 5 phút!"
        val scheduleId = inputData.getString("scheduleId") ?: ""

        createNotificationChannel()
        showNotification(title, message, scheduleId)

        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Hoạt động sắp tới",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Thông báo cho các hoạt động trong lịch trình"
            enableVibration(true)
            // Có thể thêm âm thanh tùy chỉnh
            //setSound("android.resource://${applicationContext.packageName}/raw/notification_sound".toUri(), null)
        }
        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(title: String, message: String, scheduleId: String) {
        // Intent để mở MainActivity khi nhấn thông báo
        val intent = Intent(applicationContext, MainUserActivity::class.java).apply {
            putExtra("scheduleId", scheduleId)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent cho nút "Hoãn 10 phút"
        val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java).apply {
            putExtra("notificationId", Random().nextInt())
            putExtra("title", title)
            putExtra("message", message)
            putExtra("scheduleId", scheduleId)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            Random().nextInt(),
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val completeIntent = Intent(applicationContext, CompleteReceiver::class.java).apply {
            putExtra("notificationId", Random().nextInt())
            putExtra("scheduleId", scheduleId)
        }
        val completePendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            Random().nextInt(),
            completeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(
                applicationContext.resources,
                R.drawable.img_ha_long
            ))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.img_ha_long))
                    .bigLargeIcon(null as Bitmap?)
                    .setBigContentTitle(title)
                    .setSummaryText(message)
            )
            .addAction(R.drawable.ic_notification, "Hoãn 3 phút", snoozePendingIntent)
            .addAction(R.drawable.ic_check_circle, "Hoàn thành", completePendingIntent)
            .build()

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(Random().nextInt(), notification)
    }

    companion object {
        const val CHANNEL_ID = "schedule_channel"
    }
}