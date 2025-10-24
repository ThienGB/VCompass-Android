package com.example.vcompass.screen.schedule

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.accessed.core.compose_view.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.data.api.model.Schedule
import com.example.vcompass.helper.BottomSheetType
import com.example.vcompass.ui.components.sheet.CustomBottomSheet
import com.example.vcompass.ui.core.icon.MoreOptionIcon
import com.example.vcompass.util.clickableWithScale
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.compose_view.image.CoreImage
import com.vcompass.core.compose_view.image.CoreImageSource
import com.vcompass.core.compose_view.space.ExpandableSpacer
import com.vcompass.core.compose_view.space.SpaceHeight
import com.vcompass.core.compose_view.space.SpaceWidth4
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography
import com.vcompass.core.typography.CoreTypographyBold
import com.vcompass.core.typography.CoreTypographySemiBold
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ScheduleScreen(
    navController: NavController,
) {
    val bottomSheetState = remember { mutableStateOf(BottomSheetType.NONE) }
    fun showBottomSheet(sheet: BottomSheetType) {
        if (bottomSheetState.value != sheet) {
            bottomSheetState.value = sheet
        }
    }
    // RequestNotificationPermission()
    Column(modifier = Modifier.background(Color.White)) {
        ScheduleHeader(showBottomSheet = { showBottomSheet(it) })
    }
    Box(
        modifier = Modifier
            .zIndex(2f)
            .then(
                if (bottomSheetState.value == BottomSheetType.COMMENT
                    || bottomSheetState.value == BottomSheetType.ADD_ACTIVITY
                )
                    Modifier.fillMaxHeight(0.94f) else Modifier
            )
    ) {
        CustomBottomSheet(
            bottomSheetState = bottomSheetState,
            { bottomSheetState.value = BottomSheetType.NONE },
            bottomSheetContents = mapOf(
                BottomSheetType.TIME_PICKER to {
//                        TimePickerBottomSheet(onDismiss = {
//                            bottomSheetState.value = BottomSheetType.NONE
//                        }, viewModel)
                },
                BottomSheetType.RANGE_DAY_PICKER to {
//                        DayPickerBottomSheet(onDismiss = {
//                            bottomSheetState.value = BottomSheetType.NONE
//                        }, viewModel)
                },
                BottomSheetType.COMMENT to {
//                        CommentBottomSheet(viewModel)
                },
                BottomSheetType.PRICE_PICKER to {
//                        PriceBottomSheet(onDismiss = {
//                            bottomSheetState.value = BottomSheetType.NONE
//                        }, viewModel)
                },
                BottomSheetType.ADD_ACTIVITY to {
//                        AddActivityBottomSheet()
                },
                BottomSheetType.DRAG_ITEM to {
//                        DraggableBottomSheet(onDismiss = {
//                            bottomSheetState.value = BottomSheetType.NONE
//                        }, viewModel)
                },
            )
        )
    }
}

@Composable
fun ScheduleHeader(
    showBottomSheet: (BottomSheetType) -> Unit,
    schedule: Schedule? = null
) {
    val scrollState = rememberScrollState()
    val alpha by animateFloatAsState(
        targetValue = when {
            scrollState.value < 150 -> 1f
            scrollState.value in 150..300 -> 1f - ((scrollState.value - 150) / 100f)
            else -> 0f
        }, label = "Box Alpha"
    )
    val activeScheduleId = remember { mutableStateOf("") }
    var scheduleName by remember { mutableStateOf(schedule?.scheduleName) }
    val activity = LocalActivity.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        snapshotFlow { scheduleName }
            .distinctUntilChanged()
            .collect { newName ->
                val newSchedule = schedule?.copy(scheduleName = newName)
                // viewModel.updateSchedule(newSchedule)
            }
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .graphicsLayer { this.alpha = alpha }
                .zIndex(1f)
        ) {
            CoreImage(
                source = CoreImageSource.Url(schedule?.imgSrc?.firstOrNull() ?: ""),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p170)
            )
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MyDimen.p10, vertical = MyDimen.p6)
                ) {
                    CoreIcon(
                        resDrawable = R.drawable.ic_home_fill,
                        iconModifier = Modifier.size(MyDimen.p24),
                        boxModifier = Modifier.clickable { activity?.finish() }
                    )
                    ExpandableSpacer()
                    ScheduleAuthorSection()
                }
                SpaceHeight()
                Surface(
                    shape = RoundedCornerShape(MyDimen.p8),
                    shadowElevation = MyDimen.p8,
                    color = MyColor.White,
                    modifier = Modifier
                        .padding(horizontal = MyDimen.p12)
                        .fillMaxWidth()
                        .zIndex(2f)
                ) {
                    Row(
                        modifier = Modifier.padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                value = scheduleName.toString(),
                                onValueChange = { scheduleName = it },
                                textStyle = CoreTypographyBold.bodyMedium,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    disabledTextColor = Color.Black
                                ),
                                minLines = 2,
                                maxLines = 2,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(IntrinsicSize.Min),
                            )
                            Row(
                                modifier = Modifier.padding(horizontal = 15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.clickable {
                                    showBottomSheet(
                                        BottomSheetType.RANGE_DAY_PICKER
                                    )
                                }) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CoreIcon(
                                            resDrawable = R.drawable.ic_calendar,
                                            iconModifier = Modifier.size(20.dp)
                                        )
                                        SpaceWidth4()
                                        CoreText(
                                            text = schedule?.dateStart.toString() + " - " + schedule?.dateEnd.toString(),
                                            style = CoreTypographySemiBold.displayMedium
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(MyDimen.p2))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        CoreIcon(
                                            resDrawable = R.drawable.ic_moon_24dp,
                                            iconModifier = Modifier.size(20.dp)
                                        )
                                        SpaceWidth4()
                                        CoreText(
                                            text = schedule?.numDays.toString() + " ngày " + (schedule?.numDays?.minus(
                                                1
                                            )) + " đêm",
                                            style = CoreTypographySemiBold.displayMedium
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .background(
                                            MyColor.Primary,
                                            shape = CircleShape
                                        )
                                        .padding(vertical = MyDimen.p6, horizontal = MyDimen.p10)
                                        .clickableWithScale {
//                                            if (activeScheduleId.value == schedule.id.toString()) {
//                                                viewModel.stopSchedule(context)
//                                                Toast.makeText(
//                                                    context,
//                                                    "Đã dừng lịch trình ${schedule.scheduleName}",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
//                                            } else {
//                                                viewModel.startSchedule(
//                                                    schedule.id.toString(),
//                                                    context
//                                                )
//                                                Toast.makeText(
//                                                    context,
//                                                    "Bắt đầu theo dõi lịch trình: ${schedule.scheduleName}",
//                                                    Toast.LENGTH_SHORT
//                                                ).show()
//                                            }
                                        }
                                ) {
                                    CoreIcon(
                                        resDrawable =
                                            if (activeScheduleId.value != schedule?.id.toString()) R.drawable.ic_explore_filled_24dp
                                            else R.drawable.ic_pause_circle_24dp,
                                        tintColor = MyColor.White,
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    CoreText(
                                        text = if (activeScheduleId.value != schedule?.id.toString()) "Bắt đầu" else "Dừng lại",
                                        style = CoreTypography.displayMedium,
                                        color = MyColor.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                MoreOptionIcon()
                            }
                        }
                    }
                }
            }
        }
        ScheduleTab(
            showBottomSheet = { showBottomSheet(it) },
            scrollState = scrollState,
            schedule
        )
    }
}

@Composable
fun ScheduleAuthorSection() {
    val avatars = listOf(
        "https://example.com/avatar1.jpg",
        "https://example.com/avatar2.jpg",
        "https://example.com/avatar3.jpg"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(MyDimen.p36)
    ) {
        avatars.forEachIndexed { index, url ->
            Box(
                modifier = Modifier
                    .size(MyDimen.p36)
                    .offset(x = (-10 * index).dp)
                    .zIndex((avatars.size - index).toFloat())
                    .background(Color.White, CircleShape)
                    .border(MyDimen.p1, Color.LightGray, CircleShape)
                    .padding(MyDimen.p2)
            ) {
                CoreImage(
                    source = CoreImageSource.Url(url),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }
    }
}






