package com.example.vcompass.ui.components.sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vcompass.R
import com.example.vcompass.ui.module.user.schedule.ScheduleViewModel

@Composable
fun PriceBottomSheet(
    onDismiss: () -> Unit = {},
    viewModel: ScheduleViewModel,
){
    val schedule = viewModel.schedule
    val activityItem = schedule?.activities
        ?.flatMap { it.activity ?: emptyList() }
        ?.find { it.id == viewModel.activityId }
    var cost by remember { mutableStateOf(activityItem?.cost) }
    var costName by remember { mutableStateOf(activityItem?.costDescription) }

    fun onDone(){
        val updatedActivityItem = activityItem?.copy(
            cost = cost ?: 0,
            costDescription = costName.orEmpty()
        )
        val newSchedule = schedule?.copy(
            activities = schedule.activities?.map { dayActivity ->
                dayActivity.copy(
                    day = dayActivity.day,
                    activity = dayActivity.activity?.map { activity ->
                        if (activity.id == viewModel.activityId) {
                            updatedActivityItem ?: activity
                        } else {
                            activity
                        }
                    }
                )
            }
        )
        if (newSchedule != null) {
            viewModel.updateSchedule(newSchedule)
        }
        onDismiss()
    }
    val type = when (activityItem?.activityType) {
        "Attraction" -> "Tham quan"
        "Accommodation" -> "Nghỉ ngơi"
        "FoodService" -> "Ẩm thực"
        else -> "Khác"
    }
    val typeIcon = when (activityItem?.activityType) {
        "Attraction" -> R.drawable.ic_beach
        "Accommodation" -> R.drawable.ic_accommodation_selected
        "FoodService" -> R.drawable.ic_foodservice_selected
        else -> R.drawable.ic_notification
    }
    Column (modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(horizontal = 10.dp)) {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Chi phí",
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 35.dp)
            )
            Text(
                text = "Xong",
                color = Color.Gray,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onDone() }
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_dollar_bag),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            TextField(
                value = cost.toString(),
                onValueChange = {
                    it.toIntOrNull()?.let { newCost ->
                        cost = newCost
                    }
                },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                suffix  = { Text("₫", textAlign = TextAlign.End) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End,
                    fontSize = 15.sp, fontWeight = FontWeight.W600),

            )
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(typeIcon),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = type,
                fontWeight = FontWeight.W600,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp)
            )
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Row (verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_note),
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            TextField(
                value = costName.toString(),
                onValueChange = { costName = it },
                modifier = Modifier.weight(1f),
                placeholder  = { Text("Tên chi phí...", textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End,
                    fontSize = 15.sp,color = Color.Gray, fontWeight = FontWeight.W400),

                )
        }
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(100.dp))
    }
}
