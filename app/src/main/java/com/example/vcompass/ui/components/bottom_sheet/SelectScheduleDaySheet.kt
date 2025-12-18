package com.example.vcompass.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.bottom_sheet.BaseBottomSheet
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.text.CoreText
import com.vcompass.presentation.model.schedule.DayActivity
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun SelectScheduleDaySheet(
    sheetState: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    viewModel: ScheduleViewModel = koinViewModel()
) {
    val schedule by viewModel.schedule.collectAsState()
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
    var showConfirmTrim by remember { mutableStateOf(false) }
    var selectedStartDate by remember {
        mutableStateOf(schedule.dateStart?.let { LocalDate.parse(it, formatter) })
    }
    var selectedEndDate by remember {
        mutableStateOf(schedule.dateEnd?.let { LocalDate.parse(it, formatter) })
    }

    fun trimDays() {
        val newNumDays = (selectedEndDate!!.dayOfYear - selectedStartDate!!.dayOfYear) + 1
        val trimmedDays = (schedule.days ?: emptyList()).take(newNumDays)

        val newSchedule = schedule.copy(
            dateStart = selectedStartDate!!.format(formatter),
            dateEnd = selectedEndDate!!.format(formatter),
            numDays = newNumDays,
            days = trimmedDays
        )

        viewModel.updateSchedule(newSchedule)
        onDismiss()
    }


    fun onDone() {
        if (selectedStartDate == null || selectedEndDate == null) return

        val start = minOf(selectedStartDate!!, selectedEndDate!!)
        val end = maxOf(selectedStartDate!!, selectedEndDate!!)

        val newNumDays = ChronoUnit.DAYS.between(start, end).toInt() + 1
        val oldDays = schedule.days ?: emptyList()
        val oldNum = oldDays.size
        if (newNumDays < oldNum) {
            showConfirmTrim = true
            return
        }

        val updatedDays = if (newNumDays > oldNum) {
            oldDays + List(newNumDays - oldNum) { index ->
                DayActivity(
                    day = oldNum + index + 1,
                    activity = emptyList()
                )
            }
        } else {
            oldDays
        }

        val newSchedule = schedule.copy(
            dateStart = selectedStartDate!!.format(formatter),
            dateEnd = selectedEndDate!!.format(formatter),
            numDays = newNumDays,
            days = updatedDays
        )

        viewModel.updateSchedule(newSchedule)
        onDismiss()
    }

    BaseBottomSheet(
        bottomSheetState = sheetState,
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.White)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CoreText(
                    text = stringResource(R.string.lb_day_start_and_end),
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
            SpaceHeight()
            DateRangePicker(
                selectedStartDate = selectedStartDate,
                selectedEndDate = selectedEndDate,
                onStartDateSelected = { startDate ->
                    selectedStartDate = startDate
                },
                onEndDateSelected = { endDate ->
                    selectedEndDate = endDate
                }
            )
        }
    }
    if (showConfirmTrim) {
        AlertDialog(
            onDismissRequest = { showConfirmTrim = false },
            title = { Text("Giảm số ngày lịch trình") },
            text = { Text("Bạn đang chọn ít ngày hơn hiện tại. Các hoạt động sau ngày mới sẽ bị xóa. Bạn có chắc muốn tiếp tục?") },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmTrim = false
                    trimDays()
                }) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmTrim = false }) {
                    Text("Hủy")
                }
            }
        )
    }
}