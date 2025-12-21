package com.example.vcompass.ui.components.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.bottom_sheet.BaseBottomSheet
import com.example.vcompass.ui.core.picker.TimePickerDialog
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceWidth
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.util.clickNoRipple
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddActivityTimeSheet(
    sheetState: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    viewModel: ScheduleViewModel = koinViewModel(),
) {
    var dialogType by remember { mutableStateOf("") }
    val currentActivity = viewModel.currentActivity
    var timeStart by remember { mutableStateOf(currentActivity.timeStart) }
    var timeEnd by remember { mutableStateOf(currentActivity.timeEnd) }
    fun onDone() {
        viewModel.addActivityTime(timeStart, timeEnd)
        onDismiss()
    }
    if (dialogType != "") {
        TimePickerDialog(
            initialHour = 12,
            initialMinute = 0,
            onConfirm = { timeState ->
                val pickedTime = "%02d:%02d".format(timeState.hour, timeState.minute)
                if (dialogType == "START") timeStart = pickedTime else timeEnd = pickedTime
                dialogType = ""
            },
            onDismiss = {
                dialogType = ""
            }
        )
    }
    BaseBottomSheet(
        bottomSheetState = sheetState,
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MyDimen.p16)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CoreText(
                    text = stringResource(R.string.lb_time),
                    textAlign = TextAlign.Center,
                    style = CoreTypographyBold.labelLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = MyDimen.p36)
                )
                CoreText(
                    text = stringResource(R.string.btn_done),
                    color = MyColor.TextColorLight,
                    style = CoreTypography.labelLarge,
                    modifier = Modifier.clickable { onDone() }
                )
            }
            SpaceHeight(MyDimen.p48)
            Row(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickNoRipple { dialogType = "START" }
                ) {
                    CoreText(
                        text = stringResource(R.string.lb_start) + " :",
                        color = MyColor.TextColorPrimary,
                        style = CoreTypographyBold.labelLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(end = MyDimen.p4)
                    )
                    CoreText(
                        text = timeStart,
                        color = MyColor.TextColorGray,
                        style = CoreTypography.labelLarge,
                    )
                }
                SpaceWidth()
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { dialogType = "END" }) {
                    CoreText(
                        text = stringResource(R.string.lb_end) + " :",
                        color = MyColor.TextColorPrimary,
                        style = CoreTypographyBold.labelLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(end = MyDimen.p4)
                    )
                    CoreText(
                        text = timeEnd,
                        color = MyColor.TextColorGray,
                        style = CoreTypography.labelLarge,
                    )
                }
            }
            SpaceHeight(MyDimen.p48)
        }
    }
}