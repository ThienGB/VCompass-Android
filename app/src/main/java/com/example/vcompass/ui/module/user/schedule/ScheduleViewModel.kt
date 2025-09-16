//package com.example.vcompass.ui.module.user.schedule
//
//import ActiveScheduleStore
//import NotificationWorker
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.util.Log
//import androidx.annotation.RequiresApi
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.work.*
//import com.example.vcompass.data.api.model.Accommodation
//import com.example.vcompass.data.api.model.Attraction
//import com.example.vcompass.data.api.model.FoodService
//import com.example.vcompass.data.api.model.Schedule
//import com.example.vcompass.service.ScheduleNotificationService
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import java.text.SimpleDateFormat
//import java.util.*
//import java.util.concurrent.TimeUnit
//
//class ScheduleViewModel() : ViewModel() {
//    var schedule by mutableStateOf<Schedule?>(null)
//        private set
//    private val _activeScheduleId = MutableStateFlow<String?>(null)
//    val activeScheduleId: StateFlow<String?> = _activeScheduleId.asStateFlow()
//    var activityId by mutableStateOf<String>("")
//    var isScheduleLoading by mutableStateOf(false)
//    var scheduleId by mutableStateOf("")
//    var dataStore: DataStore<Preferences>? = null
//    init {
//        viewModelScope.launch {
//            dataStore?.let { ActiveScheduleStore.getActiveScheduleId(it) }?.collect { id ->
//                _activeScheduleId.value = id
//            }
//        }
//    }
//
//
//
//    fun fetchSchedule(id: String) {
//        val tempId = "673721fe8199a5bb3cc3c7ed"
//        viewModelScope.launch {
//            isScheduleLoading = true
//            try {
//                schedule = RetrofitClient.apiService.getScheduleById(tempId).schedule
//            } catch (e: Exception) {
//                Log.e("API_ERROR", "Lỗi khi gọi API: ${e.message}")
//            } finally {
//                isScheduleLoading = false
//            }
//        }
//    }
//    fun updateSchedule(newSchedule: Schedule?) {
//        if (schedule != newSchedule  && newSchedule != null) {
//            schedule = newSchedule.copy()
////            viewModelScope.launch {
////                try {
////                    // Gửi yêu cầu update lên server mà không cần chờ phản hồi
////                  //  RetrofitClient.apiService.updateSchedule(newSchedule.id, newSchedule)
////                    // Dữ liệu trên server đã được cập nhật, nhưng không cần cập nhật ngay lập tức trên UI
////                    // Bạn vẫn giữ schedule cũ trong ViewModel nếu không cần thay đổi UI ngay
////                    Log.d("ScheduleViewModel", "Schedule updated successfully (background update).")
////                } catch (e: Exception) {
////                    Log.e("API_ERROR", "Lỗi khi cập nhật schedule: ${e.message}")
////                }
////            }
//        } else {
//            Log.d("ScheduleViewModel", "Schedule is unchanged. No update needed.")
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun startSchedule(scheduleId: String, context: Context) {
//        stopSchedule(context)
//        viewModelScope.launch {
//            // Cập nhật lịch trình đang hoạt động
//            dataStore?.let { ActiveScheduleStore.setActiveScheduleId(it, scheduleId) }
//            _activeScheduleId.value = scheduleId
//
//            schedule?.let {
//                scheduleNotifications(it, context)
//                val serviceIntent = Intent(context, ScheduleNotificationService::class.java).apply {
//                    putExtra("scheduleId", scheduleId)
//                }
//                context.startForegroundService(serviceIntent)}
//        }
//    }
//
//    fun stopSchedule(context: Context) {
//        viewModelScope.launch {
//            // Hủy tất cả thông báo
//            WorkManager.getInstance(context).cancelAllWorkByTag("schedule_notifications")
//            // Xóa lịch trình đang hoạt động
//            dataStore?.let { ActiveScheduleStore.setActiveScheduleId(it, null) }
//            context.stopService(Intent(context, ScheduleNotificationService::class.java))
//            _activeScheduleId.value = null
//        }
//    }
//    fun scheduleNotifications(schedule: Schedule, context: Context) {
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
//        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val currentTime = Calendar.getInstance()
//
//        // Parse ngày bắt đầu lịch trình
//        val startDate = Calendar.getInstance().apply {
//            time = dateFormat.parse(schedule.dateStart) ?: return
//        }
//
//        schedule.activities?.forEach { activityDay ->
//            activityDay.activity?.forEach { activity ->
//                // Tính thời gian hoạt động dựa trên ngày và giờ
//                val activityDate = Calendar.getInstance().apply {
//                    time = startDate.time
//                    activityDay.day?.let { add(Calendar.DAY_OF_MONTH, it - 1) }
//                    val parsedTime = timeFormat.parse(activity.timeStart) ?: return@forEach
//                    set(Calendar.HOUR_OF_DAY, parsedTime.hours)
//                    set(Calendar.MINUTE, parsedTime.minutes)
//                    set(Calendar.SECOND, 0)
//                    // Trừ 5 phút để thông báo trước
//                    add(Calendar.MINUTE, -5)
//                }
//
//                // Bỏ qua nếu hoạt động đã qua
//                if (activityDate.before(currentTime)) return@forEach
//
//                val delay = activityDate.timeInMillis - currentTime.timeInMillis
//                val type = when (activity.activityType) {
//                    "Attraction" -> "Tham quan"
//                    "Accommodation" -> "Nghỉ ngơi"
//                    "FoodService" -> "Ẩm thực"
//                    else -> "Khác"
//                }
//                val attraction = activity.destination as? Attraction
//                val accommodation = activity.destination as? Accommodation
//                val foodService = activity.destination as? FoodService
//                val name = attraction?.attractionName ?: foodService?.foodServiceName ?: accommodation?.description ?: activity.name
//                val description = activity.description ?: "Không có mô tả"
//                // Tạo WorkRequest
//                val data = Data.Builder()
//                    .putString("title", "Hoạt động sắp tới: $type tại $name")
//                    .putString("message", "Bạn đã sẵn sàng cho hoạt động tiếp theo chưa? \n Mô tả: $description"  )
//                    .build()
//
//                val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
//                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
//                    .setInputData(data)
//                    .addTag("schedule_notifications")
//                    .build()
//
//                WorkManager.getInstance(context).enqueueUniqueWork(
//                    "${schedule.id}_${activity.idDestination}_${activityDay.day}",
//                    ExistingWorkPolicy.REPLACE,
//                    workRequest
//                )
//            }
//        }
//    }
//    override fun onCleared() {
//        super.onCleared()
//      //  firestoreNotiManager.removeListener()
//    }
//}