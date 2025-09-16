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
fun ConnectionNotificationsScreen(
    onBack: () -> Unit = {}
) {
    var isCheckedAll by remember { mutableStateOf(false) }
    var isCheckedWhoSent by remember { mutableStateOf(false) }              // Who sent
    var isCheckedWhoAccept by remember { mutableStateOf(false) }            // Who accept
    var isCheckedConnectionFollowed by remember { mutableStateOf(false) }   // Connection followed
    var isCheckedRequestToJoin by remember { mutableStateOf(false) }        // Request to join
    var isCheckedConnectionUpdated by remember { mutableStateOf(false) }    // Connection updated
    var isCheckedSuggestionInterested by remember { mutableStateOf(false) } // Suggestion interested

    fun updateCheckedAll() {
        isCheckedAll = isCheckedWhoSent ||
                isCheckedWhoAccept ||
                isCheckedConnectionFollowed ||
                isCheckedRequestToJoin ||
                isCheckedConnectionUpdated ||
                isCheckedSuggestionInterested
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
            text = stringResource(R.string.lb_connection_settings_title)
        )

        // All profile notification
        SettingItem(
            title = stringResource(R.string.lb_network_notification_header),
            isHaveSwitch = true,
            fontWeight = FontWeight.W500,
            isChecked = isCheckedAll,
            onCheckedChange = {
                isCheckedAll = it
                isCheckedWhoSent = it
                isCheckedWhoAccept = it
                isCheckedConnectionFollowed = it
                isCheckedRequestToJoin = it
                isCheckedConnectionUpdated = it
                isCheckedSuggestionInterested = it
            }
        )

        // Who sent
        SettingItem(
            title = stringResource(R.string.lb_network_notification_who_sent),
            isHaveSwitch = true,
            isChecked = isCheckedWhoSent,
            onCheckedChange = {
                isCheckedWhoSent = it
                updateCheckedAll()
            }
        )

        // Who accept
        SettingItem(
            title = stringResource(R.string.lb_network_notification_you_are),
            isHaveSwitch = true,
            isChecked = isCheckedWhoAccept,
            onCheckedChange = {
                isCheckedWhoAccept = it
                updateCheckedAll()
            }
        )

        // Connection followed
        SettingItem(
            title = stringResource(R.string.lb_network_connection_followed),
            isHaveSwitch = true,
            isChecked = isCheckedConnectionFollowed,
            onCheckedChange = {
                isCheckedConnectionFollowed = it
                updateCheckedAll()
            }
        )

        // Request to join
        SettingItem(
            title = stringResource(R.string.lb_network_request_to_join),
            isHaveSwitch = true,
            isChecked = isCheckedRequestToJoin,
            onCheckedChange = {
                isCheckedRequestToJoin = it
                updateCheckedAll()
            }
        )

        // Connection updated
        SettingItem(
            title = stringResource(R.string.lb_network_notification_update_from),
            isHaveSwitch = true,
            isChecked = isCheckedConnectionUpdated,
            onCheckedChange = {
                isCheckedConnectionUpdated = it
                updateCheckedAll()
            }
        )

        // Suggestion interested
        SettingItem(
            title = stringResource(R.string.lb_network_notification_suggestion),
            isHaveSwitch = true,
            isChecked = isCheckedSuggestionInterested,
            onCheckedChange = {
                isCheckedSuggestionInterested = it
                updateCheckedAll()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ConnectionNotificationsScreenPreview() {
    ConnectionNotificationsScreen()
}
