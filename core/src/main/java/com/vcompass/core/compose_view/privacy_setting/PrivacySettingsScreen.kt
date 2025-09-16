package com.vcompass.core.compose_view.privacy_setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.compose_view.AccessedBottomSheet
import com.vcompass.core.compose_view.ButtonNoIcon
import com.vcompass.core.compose_view.SettingItem
import com.vcompass.core.compose_view.TitleBarAction

data class PrivacyOption(
    val title: String,
    val hasSwitch: Boolean = true,
    var isChecked: Boolean = false,
    val onToggle: ((Boolean) -> Unit)? = null,
    val onClick: (() -> Unit)? = null
)

@Composable
fun PrivacySettingsScreen(
    onBack: () -> Unit = {},
) {
    val showBottomSheet = remember { mutableStateOf(false) }
    val privacyLabels = stringArrayResource(id = R.array.lb_privacy_options)
    val privacyOptions = remember {
        mutableStateListOf<PrivacyOption>().apply {
            privacyLabels.forEachIndexed { index, label ->
                val hasSwitch = index == 0
                add(
                    PrivacyOption(
                        title = label,
                        hasSwitch = hasSwitch,
                        isChecked = false,
                        onToggle = { checked ->
                            this[index] = this[index].copy(isChecked = checked)
                        },
                        onClick = if (!hasSwitch) {
                            {
                                if (label == privacyLabels[lastIndex]){
                                    // TODO: Need to handle navigate if BlockUser
                                } else {
                                    showBottomSheet.value = true
                                }
                            }
                        } else null
                    )
                )
            }
        }
    }
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
            text = stringResource(R.string.lb_privacy_title)
        )
        LazyColumn {
            items(privacyOptions.size) { i ->
                val option = privacyOptions[i]
                SettingItem(
                    title = option.title,
                    isHaveSwitch = option.hasSwitch,
                    isChecked = option.isChecked,
                    onCheckedChange = { checked ->
                        option.onToggle?.invoke(checked)
                    },
                    onClick = { option.onClick?.invoke() }
                )
            }
        }
    }
    AccessedBottomSheet(
        bottomSheetState = showBottomSheet,
        onDismiss = { showBottomSheet.value = false },
        isShowDragHandle = true,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Bạn có thể quản lý người dùng bị chặn tại đây...")
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    )
}

@Composable
fun PrivacySettingBottomSheet() {

}

@Preview(showSystemUi = true)
@Composable
fun PrivacySettingsScreenPreview() {
    PrivacySettingsScreen()
}
