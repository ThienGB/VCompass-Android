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
fun ScheduleNotificationsScreen(
    onBack: () -> Unit = {}
) {
    var isCheckedAll by remember { mutableStateOf(false) }
    var isCheckedEventInvitations by remember { mutableStateOf(false) }   // Event invitations
    var isCheckedEventCancellation by remember { mutableStateOf(false) } // Event cancellation
    var isCheckedEventUpdates by remember { mutableStateOf(false) }      // Event updates
    var isCheckedEventInterest by remember { mutableStateOf(false) }     // Event interested
    var isCheckedAttendeeDeclined by remember { mutableStateOf(false) }  // Attendee declined
    var isCheckedAttendeeAccepted by remember { mutableStateOf(false) }  // Attendee accept

    fun updateCheckedAll() {
        isCheckedAll = isCheckedEventInvitations ||
                isCheckedEventCancellation ||
                isCheckedEventUpdates ||
                isCheckedEventInterest ||
                isCheckedAttendeeDeclined ||
                isCheckedAttendeeAccepted
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
            text = stringResource(R.string.lb_schedule_notification_title)
        )

        // All profile notification
        SettingItem(
            title = stringResource(R.string.lb_schedule_notification_header),
            isHaveSwitch = true,
            fontWeight = FontWeight.W500,
            isChecked = isCheckedAll,
            onCheckedChange = {
                isCheckedAll = it
                isCheckedEventInvitations = it
                isCheckedEventCancellation = it
                isCheckedEventUpdates = it
                isCheckedEventInterest = it
                isCheckedAttendeeDeclined = it
                isCheckedAttendeeAccepted = it
            }
        )

// Event invitations
        SettingItem(
            title = stringResource(R.string.lb_schedule_notification_event_invitation),
            isHaveSwitch = true,
            isChecked = isCheckedEventInvitations,
            onCheckedChange = {
                isCheckedEventInvitations = it
                updateCheckedAll()
            }
        )

// Event cancellation
        SettingItem(
            title = stringResource(R.string.lb_schedule_notification_event_cancellation),
            isHaveSwitch = true,
            isChecked = isCheckedEventCancellation,
            onCheckedChange = {
                isCheckedEventCancellation = it
                updateCheckedAll()
            }
        )

// Event updates
        SettingItem(
            title = stringResource(R.string.lb_schedule_notification_event_update),
            isHaveSwitch = true,
            isChecked = isCheckedEventUpdates,
            onCheckedChange = {
                isCheckedEventUpdates = it
                updateCheckedAll()
            }
        )

// Event interested
        SettingItem(
            title = stringResource(R.string.lb_schedule_notification_event_interest),
            isHaveSwitch = true,
            isChecked = isCheckedEventInterest,
            onCheckedChange = {
                isCheckedEventInterest = it
                updateCheckedAll()
            }
        )

// Attendee declined
        SettingItem(
            title = stringResource(R.string.lb_schedule_notification_attendee_declined),
            isHaveSwitch = true,
            isChecked = isCheckedAttendeeDeclined,
            onCheckedChange = {
                isCheckedAttendeeDeclined = it
                updateCheckedAll()
            }
        )

// Attendee accept
        SettingItem(
            title = stringResource(R.string.lb_schedule_notification_attendee_accepted),
            isHaveSwitch = true,
            isChecked = isCheckedAttendeeAccepted,
            onCheckedChange = {
                isCheckedAttendeeAccepted = it
                updateCheckedAll()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScheduleNotificationsScreenPreview() {
    ScheduleNotificationsScreen()
}
