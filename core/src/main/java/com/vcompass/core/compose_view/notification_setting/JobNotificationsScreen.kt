package com.vcompass.core.compose_view.notification_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.vcompass.core.R
import com.vcompass.core.compose_view.SettingItem
import com.vcompass.core.compose_view.TitleBarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobNotificationsScreen(
    onBack: () -> Unit = {}
) {
    var isCheckedAll by remember { mutableStateOf(false) }
    var isCheckedJobAlerts by remember { mutableStateOf(false) }
    var isCheckedSuggestion by remember { mutableStateOf(false) }
    var isCheckedApplication by remember { mutableStateOf(false) }
    var isCheckedApplyJobs by remember { mutableStateOf(false) }
    fun updateCheckedAll() {
        isCheckedAll =
            isCheckedJobAlerts || isCheckedSuggestion || isCheckedApplication || isCheckedApplyJobs
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBarAction(
            isHaveActionRight = false,
            onBack = onBack,
            text = stringResource(R.string.lb_job_notification_title)
        )

        // All profile notification
        SettingItem(
            title = stringResource(R.string.lb_job_notification_header),
            isHaveSwitch = true,
            fontWeight = FontWeight.W500,
            isChecked = isCheckedAll,
            onCheckedChange = {
                isCheckedAll = it
                isCheckedJobAlerts = it
                isCheckedSuggestion = it
                isCheckedApplication = it
                isCheckedApplyJobs = it
            }
        )
        // Job Alerts
        SettingItem(
            title = stringResource(R.string.lb_job_notification_alert),
            isHaveSwitch = true,
            isChecked = isCheckedJobAlerts,
            onCheckedChange = {
                isCheckedJobAlerts = it
                updateCheckedAll()
            }
        )
        // Suggestion
        SettingItem(
            title = stringResource(R.string.lb_job_notification_suggestion),
            isHaveSwitch = true,
            isChecked = isCheckedSuggestion,
            onCheckedChange = {
                isCheckedSuggestion = it
                updateCheckedAll()
            }
        )
        // Application
        SettingItem(
            title = stringResource(R.string.lb_job_notification_application),
            isHaveSwitch = true,
            isChecked = isCheckedApplication,
            onCheckedChange = {
                isCheckedApplication = it
                updateCheckedAll()
            }
        )
        // Apply Jobs
        SettingItem(
            title = stringResource(R.string.lb_job_notification_apply),
            isHaveSwitch = true,
            isChecked = isCheckedApplyJobs,
            onCheckedChange = {
                isCheckedApplyJobs = it
                updateCheckedAll()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun JobNotificationsScreenPreview() {
    JobNotificationsScreen()
}
