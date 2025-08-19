package com.example.vcompass.ui.module.user.schedule

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.vcompass.ui.font.VCompassTheme

class ScheduleActivity : ComponentActivity() {
    private lateinit var viewModel: ScheduleViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.scheduleId = intent.getStringExtra("scheduleId") ?: ""
        setContent {
            VCompassTheme {
                Schedule(viewModel)
            }
        }
    }
}