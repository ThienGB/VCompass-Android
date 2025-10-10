package com.example.vcompass.ui.module.user.schedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.vcompass.ui.theme.VCompassTheme

class ScheduleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VCompassTheme {
                //Schedule()
            }
        }
    }
}