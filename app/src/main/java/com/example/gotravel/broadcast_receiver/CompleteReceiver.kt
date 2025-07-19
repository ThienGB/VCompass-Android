package com.example.gotravel.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.runBlocking

class CompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val scheduleId = intent.getStringExtra("scheduleId") ?: return

//        // Lưu trạng thái hoàn thành vào DataStore
//        runBlocking {
//            val dataStore: DataStore<Preferences> = context.createContext(name = "schedule_preferences")
//            dataStore.edit { preferences ->
//                val key = stringPreferencesKey("completed_activity_$scheduleId")
//                preferences[key] = "completed"
//            }
//        }
//
//        // Có thể gửi thông báo hoặc cập nhật UI (nếu cần)
//        // Ví dụ: WorkManager.getInstance(context).cancelWorkById(scheduleId)
    }
}