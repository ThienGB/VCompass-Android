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
fun ProfileNotificationsScreen(
    onBack: () -> Unit = {}
) {
    var isCheckedAll by remember { mutableStateOf(false) }
    var isCheckedEndorsements by remember { mutableStateOf(false) }
    var isCheckedWhoFollow by remember { mutableStateOf(false) }
    var isCheckedWhoViewed by remember { mutableStateOf(false) }
    fun updateCheckedAll(){
        isCheckedAll = isCheckedEndorsements || isCheckedWhoFollow || isCheckedWhoViewed
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
            text = stringResource(R.string.lb_profile_notification_title)
        )

        // All profile notification
        SettingItem(
            title = stringResource(R.string.lb_profile_notification_header),
            isHaveSwitch = true,
            fontWeight = FontWeight.W500,
            isChecked = isCheckedAll,
            onCheckedChange = {
                isCheckedAll = it
                isCheckedEndorsements = it
                isCheckedWhoFollow = it
                isCheckedWhoViewed = it
            }
        )
        // Endorsements
        SettingItem(
            title = stringResource(R.string.lb_profile_notification_endorsements),
            isHaveSwitch = true,
            isChecked = isCheckedEndorsements,
            onCheckedChange = {
                isCheckedEndorsements = it
                updateCheckedAll()
            }
        )
        // Who follow
        SettingItem(
            title = stringResource(R.string.lb_profile_notification_who_follow),
            isHaveSwitch = true,
            isChecked = isCheckedWhoFollow,
            onCheckedChange = {
                isCheckedWhoFollow = it
                updateCheckedAll()
            }
        )
        // Who viewed
        SettingItem(
            title = stringResource(R.string.lb_profile_notification_who_viewed),
            isHaveSwitch = true,
            isChecked = isCheckedWhoViewed,
            onCheckedChange = {
                isCheckedWhoViewed = it
                updateCheckedAll()
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfileNotificationsScreenPreview() {
    ProfileNotificationsScreen()
}
