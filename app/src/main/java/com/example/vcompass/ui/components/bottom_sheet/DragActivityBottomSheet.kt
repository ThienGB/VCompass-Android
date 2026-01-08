package com.example.vcompass.ui.components.bottom_sheet

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt


@Composable
fun DragActivityBottomSheet(
    sheetState: MutableState<Boolean>,
    onDismiss: () -> Unit = {},
    viewModel: ScheduleViewModel = koinViewModel()
) {
    val schedule by viewModel.schedule.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var draggedIndex by remember { mutableIntStateOf(-1) }
    var targetIndex by remember { mutableIntStateOf(-1) }
    var dragOffset by remember { mutableFloatStateOf(0f) }

    val groupedList = remember {
        mutableStateMapOf<String, MutableList<Activity>>().apply {
            schedule.days?.forEach { dayActivity ->
                val dayTitle = "Ngày ${dayActivity.day}"
                val activitiesForDay = dayActivity.activities ?: emptyList()
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
                activities = items
            )
        }
        val newSchedule = schedule.copy(days = newActivities)
        viewModel.updateSchedule(newSchedule)
    }

    fun moveItem(fromIndex: Int, toIndex: Int) {
        val flat = flatList.value
        val fromItem = flat.getOrNull(fromIndex)

        // Chỉ chặn kéo header
        if (fromItem?.second == null) return

        // Validate toIndex
        if (toIndex < 0 || toIndex >= flat.size) return

        val newList = flat.toMutableList()
        val item = newList.removeAt(fromIndex)

        // Điều chỉnh toIndex nếu cần sau khi remove
        val adjustedToIndex = if (toIndex > fromIndex) toIndex else toIndex
        newList.add(adjustedToIndex, item)

        // Cập nhật groupedList
        groupedList.clear()
        var currentGroup: String? = null

        newList.forEach { (group, activity) ->
            if (activity == null) {
                currentGroup = group
                groupedList[currentGroup!!] = mutableListOf()
            } else {
                currentGroup?.let {
                    groupedList[it]?.add(activity)
                }
            }
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
                        .padding(start = MyDimen.p36)
                )
                CoreText(
                    text = stringResource(R.string.btn_done),
                    color = MyColor.TextColorLight,
                    style = CoreTypography.labelLarge,
                    modifier = Modifier.clickable {
                        updateSchedule()
                        onDismiss()
                    }
                )
            }
            SpaceHeight()
            ItemDivider()
            SpaceWidth8()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(MyDimen.p8),
            ) {
                itemsIndexed(
                    items = flatList.value,
                    key = { idx, pair ->
                        val (group, item) = pair
                        // Tạo unique key cho mỗi item
                        if (item != null) {
                            "item-${group}-${item.hashCode()}-$idx"
                        } else {
                            "header-$group-$idx"
                        }
                    }
                ) { index, (group, item) ->
                    if (item == null) {
                        // Header - có thể bị đẩy lên/xuống
                        val animatedOffset = remember { Animatable(0f) }

                        LaunchedEffect(draggedIndex, targetIndex, index) {
                            if (draggedIndex != -1) {
                                // Header nằm giữa draggedIndex và targetIndex sẽ bị đẩy
                                val shouldMove = when {
                                    // Kéo xuống: header từ draggedIndex+1 đến targetIndex phải dịch lên
                                    targetIndex > draggedIndex && index in (draggedIndex + 1)..targetIndex -> -1f
                                    // Kéo lên: header từ targetIndex đến draggedIndex-1 phải dịch xuống
                                    targetIndex < draggedIndex && index in targetIndex until draggedIndex -> 1f
                                    else -> 0f
                                }

                                animatedOffset.animateTo(
                                    shouldMove * 88f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                )
                            } else {
                                animatedOffset.animateTo(
                                    0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier.offset {
                                IntOffset(0, animatedOffset.value.roundToInt())
                            }
                        ) {
                            CoreText(
                                text = group,
                                style = CoreTypographyBold.displayLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = MyDimen.p16, vertical = MyDimen.p6),
                            )
                        }
                    } else {
                        // Draggable Item
                        val isDragging = draggedIndex == index
                        val isTarget = targetIndex == index

                        // Tính toán offset cho item này dựa vào vị trí drag
                        val animatedOffset = remember { Animatable(0f) }

                        LaunchedEffect(draggedIndex, targetIndex, index) {
                            if (draggedIndex != -1 && !isDragging) {
                                // Item này nằm giữa draggedIndex và targetIndex
                                val shouldMove = when {
                                    // Kéo xuống: các item từ draggedIndex+1 đến targetIndex phải dịch lên
                                    targetIndex > draggedIndex && index in (draggedIndex + 1)..targetIndex -> -1f
                                    // Kéo lên: các item từ targetIndex đến draggedIndex-1 phải dịch xuống
                                    targetIndex < draggedIndex && index in targetIndex until draggedIndex -> 1f
                                    else -> 0f
                                }

                                animatedOffset.animateTo(
                                    shouldMove * 88f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                )
                            } else {
                                animatedOffset.animateTo(
                                    0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                )
                            }
                        }

                        // Offset cho item đang được drag
                        val dragAnimOffset = remember { Animatable(0f) }

                        LaunchedEffect(dragOffset) {
                            if (isDragging) {
                                dragAnimOffset.snapTo(dragOffset)
                            }
                        }

                        LaunchedEffect(isDragging) {
                            if (!isDragging) {
                                dragAnimOffset.animateTo(
                                    0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .zIndex(if (isDragging) 1f else 0f)
                                .offset {
                                    IntOffset(
                                        0,
                                        if (isDragging) dragAnimOffset.value.roundToInt()
                                        else animatedOffset.value.roundToInt()
                                    )
                                }
                                .graphicsLayer {
                                    alpha = if (isDragging) 0.9f else 1f
                                    scaleX = if (isDragging) 1.02f else 1f
                                    scaleY = if (isDragging) 1.02f else 1f
                                }
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = MyDimen.p16)
                                    .border(
                                        MyDimen.pHalf,
                                        when {
                                            isDragging -> MyColor.DarkBlue
                                            isTarget -> MyColor.DarkBlue.copy(alpha = 0.3f)
                                            else -> MyColor.White
                                        },
                                        RoundedCornerShape(MyDimen.p6)
                                    )
                                    .pointerInput(index) { // Key bằng index để mỗi item có gesture riêng
                                        detectDragGesturesAfterLongPress(
                                            onDragStart = {
                                                draggedIndex = index
                                                targetIndex = index
                                                dragOffset = 0f
                                            },
                                            onDrag = { change, dragAmount ->
                                                change.consume()
                                                dragOffset += dragAmount.y

                                                // Tính toán target index
                                                val itemHeight = 88f
                                                val newTarget = (index + (dragOffset / itemHeight).roundToInt())
                                                    .coerceIn(0, flatList.value.size - 1)

                                                targetIndex = newTarget
                                            },
                                            onDragEnd = {
                                                if (draggedIndex != -1 && targetIndex != -1 && draggedIndex != targetIndex) {
                                                    var finalTarget = targetIndex

                                                    // Nếu target là header, điều chỉnh vị trí
                                                    val targetItem = flatList.value.getOrNull(finalTarget)
                                                    if (targetItem?.second == null) {
                                                        // Target là header
                                                        if (targetIndex > draggedIndex) {
                                                            // Kéo xuống: tìm item đầu tiên sau header (nếu có)
                                                            var nextItemIndex = targetIndex + 1
                                                            while (nextItemIndex < flatList.value.size) {
                                                                if (flatList.value[nextItemIndex].second != null) {
                                                                    finalTarget = nextItemIndex
                                                                    break
                                                                }
                                                                nextItemIndex++
                                                            }
                                                            // Nếu không tìm thấy item sau header, giữ nguyên target
                                                            if (nextItemIndex >= flatList.value.size) {
                                                                finalTarget = targetIndex
                                                            }
                                                        } else {
                                                            // Kéo lên: tìm item cuối cùng trước header (nếu có)
                                                            var prevItemIndex = targetIndex - 1
                                                            while (prevItemIndex >= 0) {
                                                                if (flatList.value[prevItemIndex].second != null) {
                                                                    finalTarget = prevItemIndex
                                                                    break
                                                                }
                                                                prevItemIndex--
                                                            }
                                                            // Nếu không tìm thấy item trước header, đặt sau header này
                                                            if (prevItemIndex < 0) {
                                                                finalTarget = targetIndex + 1
                                                            }
                                                        }
                                                    }

                                                    // Validate finalTarget
                                                    if (finalTarget >= 0 && finalTarget < flatList.value.size) {
                                                        moveItem(draggedIndex, finalTarget)
                                                        scope.launch {
                                                            updateSchedule()
                                                        }
                                                    }
                                                }
                                                draggedIndex = -1
                                                targetIndex = -1
                                                dragOffset = 0f
                                            },
                                            onDragCancel = {
                                                draggedIndex = -1
                                                targetIndex = -1
                                                dragOffset = 0f
                                            }
                                        )
                                    },
                                shape = RoundedCornerShape(MyDimen.p6),
                                elevation = CardDefaults.cardElevation(
                                    if (isDragging) MyDimen.p4 else MyDimen.zero
                                ),
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