package com.example.vcompass.ui.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.vcompass.R
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.bottom_sheet.BaseBottomSheet
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.vcompass.presentation.model.schedule.Activity
import com.vcompass.presentation.model.schedule.DayActivity
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable
import org.koin.androidx.compose.koinViewModel


@Composable
fun DragActivityBottomSheet(
    sheetState: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    viewModel: ScheduleViewModel = koinViewModel()
) {
    val schedule by viewModel.schedule.collectAsState()
    val isDragging = remember { mutableStateOf(false) }
    val groupedList = remember {
        mutableStateMapOf<String, MutableList<Activity>>().apply {
            schedule.days?.forEach { dayActivity ->
                val dayTitle = "Ngày ${dayActivity.day}"
                val activitiesForDay = dayActivity.activity ?: emptyList()
                this[dayTitle] = activitiesForDay.toMutableList()
            }
        }
    }
    val flatList = remember {
        derivedStateOf {
            buildList {
                groupedList.forEach { (group, items) ->
                    add(group to null) // Header
                    addAll(items.map { group to it }) // Items
                }
            }
        }
    }

    fun updateSchedule() {
        val newActivities = groupedList.entries.map { (group, items) ->
            val dayNumber = group.removePrefix("Ngày ").toIntOrNull() ?: 0
            DayActivity(
                day = dayNumber,
                activity = items
            )
        }
        val newSchedule = schedule.copy(days = newActivities)

    }

    val reorderState = rememberReorderableLazyListState(
        onMove = { from, to ->
            val flat = flatList.value
            val fromItem = flat.getOrNull(from.index)
            val toItem = flat.getOrNull(to.index)

            // Chặn kéo nếu là group header
            if (fromItem?.second == null) return@rememberReorderableLazyListState

            isDragging.value = true

            val newList = flat.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }

            // Làm mới groupedList
            groupedList.clear()
            var currentGroup: String? = null

            newList.forEach { (group, item) ->
                if (item == null) {
                    // Đây là header ngày
                    currentGroup = group
                    groupedList[currentGroup] = mutableListOf()
                } else {
                    // Gán vào nhóm hiện tại
                    currentGroup?.let {
                        groupedList[it]?.add(item)
                    }
                }
            }
        },
    )
    LaunchedEffect(isDragging.value) {
        if (!isDragging.value && flatList.value.isNotEmpty()) {
            updateSchedule()
        }
    }
    LaunchedEffect(reorderState.draggingItemIndex) {
        val dragging = reorderState.draggingItemIndex != null
        isDragging.value = dragging
        if (!dragging && flatList.value.isNotEmpty()) {
            updateSchedule()
        }
    }

    BaseBottomSheet(
        bottomSheetState = sheetState,
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .background(MyColor.White)
                .padding(horizontal = MyDimen.p16)
                .padding(bottom = MyDimen.p30)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CoreText(
                    text = stringResource(R.string.lb_reorder),
                    textAlign = TextAlign.Center,
                    style = CoreTypographyBold.labelLarge,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 35.dp)
                )
                CoreText(
                    text = stringResource(R.string.btn_done),
                    color = MyColor.TextColorLight,
                    style = CoreTypography.labelLarge,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }
            SpaceHeight()
            ItemDivider()
            SpaceWidth8()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .reorderable(reorderState)
                    .detectReorderAfterLongPress(reorderState),
                state = reorderState.listState,
                verticalArrangement = Arrangement.spacedBy(MyDimen.p8),
            ) {
                itemsIndexed(flatList.value, key = { _, pair ->
                    val (group, item) = pair
                    item?.let { "$group-$it" } ?: "header-$group"
                }) { _, (group, item) ->
                    if (item == null) {
                        CoreText(
                            text = group,
                            style = CoreTypographyBold.displayLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = MyDimen.p16, vertical = MyDimen.p6),
                        )
                    } else {
                        ReorderableItem(reorderState, key = "$group-$item") { isDragging ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = MyDimen.p16)
                                    .border(
                                        MyDimen.pHalf,
                                        MyColor.DarkBlue.takeIf { isDragging } ?: MyColor.White,
                                        RoundedCornerShape(MyDimen.p6)
                                    ),
                                shape = RoundedCornerShape(MyDimen.p6),
                                elevation = CardDefaults.cardElevation(if (isDragging) MyDimen.p4 else MyDimen.zero),
                                colors = CardDefaults.cardColors(MyColor.White),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = MyDimen.p8)
                                ) {
                                    CoreText(
                                        text = item.business.name ?: item.name,
                                        style = CoreTypography.labelMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(
                                                horizontal = MyDimen.p8,
                                                vertical = MyDimen.p16
                                            )
                                    )
                                    CoreIcon(
                                        imageVector = Icons.Rounded.DragIndicator,
                                        iconModifier = Modifier.size(MyDimen.p20)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            SpaceHeight()
        }
    }
}