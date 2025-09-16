//package com.example.vcompass.ui.components.sheet
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import com.example.vcompass.ui.components.TimePickerDialog
//import com.example.vcompass.ui.module.user.schedule.ScheduleViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TimePickerBottomSheet(
//    onDismiss: () -> Unit = {},
//    viewModel: ScheduleViewModel,
//){
//    var showDialog by remember { mutableStateOf("") }
//    val schedule = viewModel.schedule
//    val activityItem = schedule?.activities
//        ?.flatMap { it.activity ?: emptyList() }
//        ?.find { it.id == viewModel.activityId }
//    var timeStart by remember { mutableStateOf(activityItem?.timeStart) }
//    var timeEnd by remember { mutableStateOf(activityItem?.timeEnd) }
//    fun onDone(){
//        val updatedActivityItem = activityItem?.copy(
//            timeStart = timeStart,
//            timeEnd = timeEnd
//        )
//        val newSchedule = schedule?.copy(
//            activities = schedule.activities?.map { dayActivity ->
//                dayActivity.copy(
//                    day = dayActivity.day,
//                    activity = dayActivity.activity?.map { activity ->
//                        if (activity.id == viewModel.activityId) {
//                            updatedActivityItem ?: activity
//                        } else {
//                            activity
//                        }
//                    }
//                )
//            }
//        )
//        if (newSchedule != null) {
//            viewModel.updateSchedule(newSchedule)
//        }
//        onDismiss()
//    }
//    if (showDialog != "") {
//        TimePickerDialog(
//            initialHour = 12,
//            initialMinute = 0,
//            onConfirm = { timeState ->
//                val pickedTime = "%02d:%02d".format(timeState.hour, timeState.minute)
//                if (showDialog == "start") timeStart = pickedTime else timeEnd = pickedTime
//                showDialog = ""
//            },
//            onDismiss = {
//                showDialog = ""
//            }
//        )
//    }
//    Column (modifier = Modifier
//        .fillMaxWidth()
//        .padding(horizontal = 15.dp)
//        .background(Color.White)) {
//        Row(modifier = Modifier.fillMaxWidth()){
//            Text(
//                text = "Thời gian",
//                fontWeight = FontWeight.W700,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 35.dp)
//            )
//            Text(
//                text = "Xong",
//                color = Color.Gray,
//                fontWeight = FontWeight.W400,
//                modifier = Modifier.clickable { onDone() }
//            )
//        }
//        Spacer(modifier = Modifier.height(70.dp))
//        Row(modifier = Modifier.fillMaxWidth()){
//            Row (modifier = Modifier
//                .weight(1f)
//                .clickable { showDialog = "start" }) {
//                Text(
//                    text = "Bắt đầu:",
//                    fontWeight = FontWeight.W700,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(end = 3.dp)
//                )
//                Text(
//                    text = timeStart.toString(),
//                    color = Color.DarkGray,
//                    fontWeight = FontWeight.W400,
//                )
//            }
//            Spacer(modifier = Modifier.width(10.dp))
//            Row (modifier = Modifier
//                .weight(1f)
//                .clickable { showDialog = "end" }) {
//                Text(
//                    text = "Kết thúc:",
//                    fontWeight = FontWeight.W700,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(end = 3.dp)
//                )
//                Text(
//                    text = timeEnd.toString(),
//                    color = Color.DarkGray,
//                    fontWeight = FontWeight.W400,
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(70.dp))
//
//    }
//}
//
