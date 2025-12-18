package com.example.vcompass.ui.core.picker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import java.time.format.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.text.CoreText
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun TimePickerDialog(
    initialHour: Int = 12,
    initialMinute: Int = 0,
    onConfirm: (TimePickerState) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = false,
    )
    var showDial by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = RoundedCornerShape(MyDimen.p16),
            tonalElevation = 6.dp,
            modifier = Modifier.padding(MyDimen.p36)
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize(animationSpec = tween(durationMillis = 300))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpaceHeight()
                MaterialTheme(
                    typography = MaterialTheme.typography.copy(
                        displayLarge = MaterialTheme.typography.displayLarge.copy(fontSize = MyDimen.s32),
                        displayMedium = MaterialTheme.typography.displayMedium.copy(fontSize = MyDimen.s20),
                        displaySmall = MaterialTheme.typography.displaySmall.copy(fontSize = MyDimen.s16),
                    )
                ) {
                    if (showDial)
                        TimePicker(
                            state = timePickerState,
                            layoutType = TimePickerLayoutType.Vertical
                        ) else TimeInput(state = timePickerState)
                }
                Row(
                    modifier = Modifier
                        .height(MyDimen.p44)
                        .padding(horizontal = MyDimen.p16)
                        .padding(bottom = MyDimen.p8)
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = { showDial = !showDial }) {
                        CoreIcon(
                            imageVector = if (showDial) Icons.Filled.Keyboard else Icons.Filled.DateRange,
                        )
                    }
                    ExpandableSpacer()
                    TextButton(onClick = onDismiss) {
                        CoreText(
                            text = stringResource(R.string.lb_cancel),
                            style = CoreTypographySemiBold.labelLarge,
                            color = MyColor.Primary
                        )
                    }

                    TextButton(onClick = { onConfirm(timePickerState) }) {
                        CoreText(
                            text = stringResource(R.string.lb_ok),
                            style = CoreTypographySemiBold.labelLarge,
                            color = MyColor.Primary
                        )
                    }
                }
            }
        }
    }
}



