package com.vcompass.core.compose_view.notification_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.compose_view.ButtonNoIcon
import com.vcompass.core.compose_view.SettingItem
import com.vcompass.core.compose_view.TitleBarAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    onBack: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBarAction(
            isHaveActionRight = false,
            onBack = onBack,
            text = stringResource(R.string.lb_notification_setting_title)
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.shadow_top_height)))
        SettingItem(
            title = stringResource(R.string.lb_notification_setting_manage_notification).uppercase(),
            isHaveSwitch = true,
            fontWeight = FontWeight.W500,
            isChecked = isChecked,
            textColor = R.color.textColorLight,
            onCheckedChange = {
                isChecked = it
            }
        )
        NotificationSettingItem(
            icon = if (isChecked) R.drawable.ic_baseline_volume_up_24
            else R.drawable.ic_baseline_volume_off_24,
            title = stringResource(R.string.lb_notification_mute),
            subTitle = if (isChecked) stringResource(R.string.lb_notification_on)
            else stringResource(R.string.lb_notification_off),
            isClickable = false
        )
        NotificationReceiveSection()
        Spacer(modifier = Modifier.weight(1f))
        ButtonNoIcon(
            text = stringResource(R.string.btn_save),
            onClick = onSaveClick,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.content_padding))
        )
    }
}

@Composable
fun NotificationSettingItem(
    icon: Int,
    title: String,
    subTitle: String,
    onClick: () -> Unit = {},
    isClickable: Boolean = true
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(enabled = isClickable, onClick = onClick)
            .padding(16.dp)
    ) {
        Icon(painter = painterResource(icon), contentDescription = null)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = dimensionResource(R.dimen.content_padding))
        ) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.W600,
                color = colorResource(R.color.textColorDark)
            )
            Text(
                text = subTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                color = colorResource(R.color.textColorLight),
            )
        }
        if (isClickable)
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right_grey_24dp),
                contentDescription = null
            )
    }
    if (isClickable)
        HorizontalDivider(
            thickness = 0.5.dp,
            color = colorResource(R.color.listItemSeparate),
            modifier = Modifier.padding(start = 56.dp, end = 30.dp)
        )
}

@Composable
fun NotificationReceiveSection() {
    SettingItem(
        title = stringResource(R.string.lb_notification_setting_section_detail_title).uppercase(),
        fontWeight = FontWeight.W500,
        textColor = R.color.textColorLight,
        isHaveRightAction = false
    )
    // Profile
    NotificationSettingItem(
        icon = R.drawable.ic_baseline_person_24,
        title = stringResource(R.string.lb_notification_profile),
        subTitle = stringResource(R.string.lb_notification_profile_desc),
        onClick = {}
    )
    // Jobs
    NotificationSettingItem(
        icon = R.drawable.ic_baseline_business_center_24,
        title = stringResource(R.string.lb_notification_job),
        subTitle = stringResource(R.string.lb_notification_job_desc),
        onClick = {}
    )
    // Schedulers
    NotificationSettingItem(
        icon = R.drawable.ic_baseline_today_24,
        title = stringResource(R.string.lb_notification_schedulers),
        subTitle = stringResource(R.string.lb_notification_schedulers_desc),
        onClick = {}
    )
    // Networks
    NotificationSettingItem(
        icon = R.drawable.ic_baseline_device_hub_24,
        title = stringResource(R.string.lb_notification_network),
        subTitle = stringResource(R.string.lb_notification_network_desc),
        onClick = {}
    )
    // Feeds
    NotificationSettingItem(
        icon = R.drawable.ic_baseline_rss_feed_24,
        title = stringResource(R.string.lb_notification_feed),
        subTitle = stringResource(R.string.lb_notification_feed_desc),
        onClick = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun NotificationSettingsScreenPreview() {
    NotificationSettingsScreen()
}
