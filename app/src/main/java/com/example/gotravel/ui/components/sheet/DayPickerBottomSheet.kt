package com.example.gotravel.ui.components.sheet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotravel.R
import com.example.gotravel.ui.module.user.schedule.ScheduleViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayPickerBottomSheet(
    onDismiss: () -> Unit = {},
    viewModel: ScheduleViewModel
) {
    val schedule = viewModel.schedule
    val formatter = remember { DateTimeFormatter.ofPattern("dd-MM-yyyy") }
    var selectedStartDate by remember {
        mutableStateOf(schedule?.dateStart?.let { LocalDate.parse(it, formatter) })
    }
    var selectedEndDate by remember {
        mutableStateOf(schedule?.dateEnd?.let { LocalDate.parse(it, formatter) })
    }
    var displayedMonth by remember { mutableStateOf(YearMonth.from(selectedStartDate ?: LocalDate.now())) }
    fun generateCalendarDays(displayedMonth: YearMonth): List<LocalDate> {
        val firstDayOfMonth = displayedMonth.atDay(1)
        val lastDayOfMonth = displayedMonth.atEndOfMonth()
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        val days = mutableListOf<LocalDate>()
        val prevMonth = displayedMonth.minusMonths(1)
        val daysInPrevMonth = prevMonth.lengthOfMonth()
        for (i in firstDayOfWeek - 1 downTo 0) {
            days.add(prevMonth.atDay(daysInPrevMonth - i))
        }
        for (i in 1..displayedMonth.lengthOfMonth()) {
            days.add(displayedMonth.atDay(i))
        }
        val nextMonth = displayedMonth.plusMonths(1)
        for (i in 1..(42 - days.size)) {
            days.add(nextMonth.atDay(i))
        }
        return days
    }
    val days by remember(displayedMonth) {
        mutableStateOf(generateCalendarDays(displayedMonth))
    }

    fun onDone(){
        val newSchedule = schedule?.copy(
            dateStart = selectedStartDate?.format(formatter),
            dateEnd = selectedEndDate?.format(formatter)
        )
        if (newSchedule != null) {
            viewModel.updateSchedule(newSchedule)
        }
        onDismiss()
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp).background(Color.White)) {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Ngày đi & về",
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 35.dp)
            )
            Text(
                text = "Xong",
                color = Color.Gray,
                fontWeight = FontWeight.W400,
                modifier = Modifier.clickable {  onDone() }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { displayedMonth = displayedMonth.minusMonths(1) }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${displayedMonth.month.getDisplayName(TextStyle.FULL, Locale("vi"))} ${displayedMonth.year}",
                    fontWeight = FontWeight.W400,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = when {
                        selectedStartDate != null && selectedEndDate != null ->
                            "${selectedStartDate!!.dayOfMonth}/${selectedStartDate!!.monthValue} - ${selectedEndDate!!.dayOfMonth}/${selectedEndDate!!.monthValue}"
                        selectedStartDate != null ->
                            "${selectedStartDate!!.dayOfMonth}/${selectedStartDate!!.monthValue} - Vui lòng chọn"
                        else -> "Hãy chọn ngày"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W700
                )
            }
            IconButton(onClick = { displayedMonth = displayedMonth.plusMonths(1) }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            listOf("CN", "T2", "T3", "T4", "T5", "T6", "T7").forEach { day ->
                Text(
                    text = day,
                    fontWeight = FontWeight.W600,
                    color = if (day == "CN") Color.Red else Color.Gray,
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(days.size) { index ->
                val date = days[index]
                val isStartDate = selectedStartDate?.let {
                    date.dayOfMonth == it.dayOfMonth &&
                            date.monthValue == it.monthValue &&
                            date.year == it.year
                } == true

                val isEndDate = selectedEndDate?.let {
                    date.dayOfMonth == it.dayOfMonth &&
                            date.monthValue == it.monthValue &&
                            date.year == it.year
                } == true
                val isInRange = selectedStartDate != null && selectedEndDate != null && date in selectedStartDate!!..selectedEndDate!!
                val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
                val isOutOfMonth = date.month != displayedMonth.month

                val backgroundColor = when {
                    isStartDate || isEndDate -> colorResource(id = R.color.blue)
                    isInRange -> colorResource(id = R.color.colorBar)
                    else -> Color.Transparent
                }

                val shape = when {
                    isStartDate -> RoundedCornerShape(topStart = 99.dp, bottomStart = 99.dp)
                    isEndDate -> RoundedCornerShape(topEnd = 99.dp, bottomEnd = 99.dp)
                    isInRange -> RoundedCornerShape(0.dp)
                    else -> RoundedCornerShape(0.dp)
                }

                val textColor = when {
                    isStartDate || isEndDate -> Color.White
                    isSunday -> Color.Red
                    isOutOfMonth -> Color.Gray.copy(alpha = 0.5f)
                    else -> Color.Black
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape)
                        .background(backgroundColor)
                        .clickable {
                            // Số ngày cố định
                            val numDays = schedule?.numDays ?: 1
                            selectedStartDate = date
                            selectedEndDate = date.plusDays(numDays.toLong())
                            // logic chọn ngày linh hoạt
//                            when {
//                                selectedStartDate == null || selectedEndDate != null -> {
//                                    selectedStartDate = date
//                                    selectedEndDate = null
//                                }
//
//                                date < selectedStartDate!! -> {
//                                    selectedStartDate = date
//                                }
//
//                                else -> {
//                                    selectedEndDate = date
//                                }
//                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        color = textColor,
                        fontSize = 16.sp,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))

    }
}