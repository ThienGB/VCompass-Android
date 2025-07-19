package com.example.vcompass.ui.module.user.schedule

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.vcompass.MainApplication
import com.example.vcompass.data.local.store.DataStoreProvider
import com.example.vcompass.helper.RealmHelper
import com.example.vcompass.ui.factory.ViewModelFactory
import com.example.vcompass.ui.font.VCompassTheme

class ScheduleActivity : ComponentActivity() {
    private lateinit var viewModel: ScheduleViewModel
    private lateinit var realmHelper: RealmHelper

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realmHelper = (application as MainApplication).realmHelper
        val dataStore = DataStoreProvider.getDataStore(this)
        val factory = ViewModelFactory(ScheduleViewModel::class.java, realmHelper)
        viewModel = ViewModelProvider(this, factory)[ScheduleViewModel::class.java]
        viewModel.dataStore = dataStore
        viewModel.scheduleId = intent.getStringExtra("scheduleId") ?: ""
        setContent {
            VCompassTheme {
                Schedule(viewModel)
            }
        }
    }
}