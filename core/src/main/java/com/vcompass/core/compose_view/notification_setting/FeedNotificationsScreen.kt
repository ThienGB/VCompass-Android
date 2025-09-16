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
fun FeedNotificationsScreen(
    onBack: () -> Unit = {}
) {
    var isCheckedAll by remember { mutableStateOf(false) }
    var isCheckedUpdateFromConnection by remember { mutableStateOf(false) }  // was isCheckedWhoSent
    var isCheckedWhoReacted by remember { mutableStateOf(false) }           // was isCheckedWhoAccept
    var isCheckedWhoWrote by remember { mutableStateOf(false) }             // was isCheckedConnectionFollowed
    var isCheckedWhoShared by remember { mutableStateOf(false) }            // was isCheckedRequestToJoin
    var isCheckedTaggedMentioned by remember { mutableStateOf(false) }      // was isCheckedConnectionUpdated
    var isCheckedWhoConnectionUpdate by remember { mutableStateOf(false) }  // was isCheckedSuggestionInterested

    fun updateCheckedAll() {
        isCheckedAll = isCheckedUpdateFromConnection ||
                isCheckedWhoReacted ||
                isCheckedWhoWrote ||
                isCheckedWhoShared ||
                isCheckedTaggedMentioned ||
                isCheckedWhoConnectionUpdate
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
            text = stringResource(R.string.lb_feed_notification_title)
        )

        // All profile notification
        SettingItem(
            title = stringResource(R.string.lb_feed_notification_header),
            isHaveSwitch = true,
            fontWeight = FontWeight.W500,
            isChecked = isCheckedAll,
            onCheckedChange = { checked ->
                isCheckedAll = checked
                isCheckedUpdateFromConnection = checked
                isCheckedWhoReacted = checked
                isCheckedWhoWrote = checked
                isCheckedWhoShared = checked
                isCheckedTaggedMentioned = checked
                isCheckedWhoConnectionUpdate = checked
            }
        )

        // Update from connection
        SettingItem(
            title = stringResource(R.string.lb_feed_notification_connection_update),
            isHaveSwitch = true,
            isChecked = isCheckedUpdateFromConnection,
            onCheckedChange = {
                isCheckedUpdateFromConnection = it
                updateCheckedAll()
            }
        )

        // Who Reacted
        SettingItem(
            title = stringResource(R.string.lb_feed_notification_who_reacted),
            isHaveSwitch = true,
            isChecked = isCheckedWhoReacted,
            onCheckedChange = {
                isCheckedWhoReacted = it
                updateCheckedAll()
            }
        )

        // Who wrote
        SettingItem(
            title = stringResource(R.string.lb_feed_notification_who_wrote),
            isHaveSwitch = true,
            isChecked = isCheckedWhoWrote,
            onCheckedChange = {
                isCheckedWhoWrote = it
                updateCheckedAll()
            }
        )

        // Who shared
        SettingItem(
            title = stringResource(R.string.lb_feed_notification_who_share),
            isHaveSwitch = true,
            isChecked = isCheckedWhoShared,
            onCheckedChange = {
                isCheckedWhoShared = it
                updateCheckedAll()
            }
        )

        // Tagged, mentioned
        SettingItem(
            title = stringResource(R.string.lb_feed_notification_who_tag_or_mention),
            isHaveSwitch = true,
            isChecked = isCheckedTaggedMentioned,
            onCheckedChange = {
                isCheckedTaggedMentioned = it
                updateCheckedAll()
            }
        )

        // Connection updated
        SettingItem(
            title = stringResource(R.string.lb_feed_notification_who_connection_update_status),
            isHaveSwitch = true,
            isChecked = isCheckedWhoConnectionUpdate,
            onCheckedChange = {
                isCheckedWhoConnectionUpdate = it
                updateCheckedAll()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FeedNotificationsScreenPreview() {
    FeedNotificationsScreen()
}
