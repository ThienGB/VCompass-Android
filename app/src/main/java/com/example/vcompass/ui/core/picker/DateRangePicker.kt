package com.example.vcompass.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight4
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.text.CoreText
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DateRangePicker(
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onStartDateSelected: (LocalDate) -> Unit,
    onEndDateSelected: (LocalDate?) -> Unit,
) {
    var displayedMonth by remember {
        mutableStateOf(
            YearMonth.from(
                selectedStartDate ?: LocalDate.now()
            )
        )
    }

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


    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { displayedMonth = displayedMonth.minusMonths(1) }) {
                CoreIcon(imageVector = Icons.AutoMirrored.Filled.ArrowBack)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CoreText(
                    text = "${
                        displayedMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
                    } ${displayedMonth.year}",
                    style = CoreTypography.labelLarge,
                    color = MyColor.TextColorLight,
                )
                SpaceHeight4()
                CoreText(
                    text = when {
                        selectedStartDate != null && selectedEndDate != null ->
                            "${selectedStartDate!!.dayOfMonth}/${selectedStartDate!!.monthValue} - ${selectedEndDate!!.dayOfMonth}/${selectedEndDate!!.monthValue}"

                        selectedStartDate != null ->
                            "${selectedStartDate!!.dayOfMonth}/${selectedStartDate!!.monthValue} - ${
                                stringResource(R.string.lb_please_choose)
                            }"

                        else -> stringResource(R.string.lb_please_choose_day)
                    },
                    style = CoreTypographyBold.labelLarge,
                    color = MyColor.TextColorPrimary,
                )
            }
            IconButton(onClick = { displayedMonth = displayedMonth.plusMonths(1) }) {
                CoreIcon(imageVector = Icons.AutoMirrored.Filled.ArrowForward)
            }
        }

        SpaceHeight()
        val weekdays = stringArrayResource(R.array.weekdays)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            weekdays.forEachIndexed { index, day ->
                CoreText(
                    text = day,
                    style = CoreTypographySemiBold.displayMedium,
                    color = if (index == 0) Color.Red else MyColor.TextColorGray,
                )
            }
        }
        SpaceHeight(MyDimen.p2)
        ItemDivider()
        SpaceHeight8()
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
                val isInRange =
                    selectedStartDate != null && selectedEndDate != null && date in selectedStartDate!!..selectedEndDate!!
                val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY
                val isOutOfMonth = date.month != displayedMonth.month

                val backgroundColor = when {
                    isStartDate || isEndDate -> MyColor.Primary
                    isInRange -> MyColor.Primary.copy(0.3f)
                    else -> Color.Transparent
                }

                val shape = when {
                    isStartDate && isEndDate -> CircleShape
                    isStartDate -> RoundedCornerShape(topStart = 99.dp, bottomStart = 99.dp)
                    isEndDate -> RoundedCornerShape(topEnd = 99.dp, bottomEnd = 99.dp)
                    isInRange -> RoundedCornerShape(0.dp)
                    else -> RoundedCornerShape(0.dp)
                }

                val textColor = when {
                    isStartDate || isEndDate -> MyColor.White
                    isSunday -> MyColor.Red
                    isOutOfMonth -> MyColor.TextColorGray.copy(alpha = 0.5f)
                    else -> MyColor.TextColorPrimary
                }

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(shape)
                        .background(backgroundColor)
                        .clickable {
//                                // Số ngày cố định
//                                val numDays = schedule?.numDays ?: 1
//                                selectedStartDate = date
//                                selectedEndDate = date.plusDays(numDays.toLong())
//                                // logic chọn ngày linh hoạt
                            when {
                                selectedStartDate == null || (selectedEndDate != null) -> {
                                    onStartDateSelected(date)
                                    onEndDateSelected(null)
                                }

                                date.isBefore(selectedStartDate) -> {
                                    onStartDateSelected(date)
                                }

                                else -> {
                                    onEndDateSelected(date)
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    CoreText(
                        text = date.dayOfMonth.toString(),
                        color = textColor,
                        style = CoreTypographyMedium.labelLarge
                    )
                }
            }
        }
        SpaceHeight(MyDimen.p48)
    }
}