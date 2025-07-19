package com.example.gotravel.ui.components.sheet

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.R
import com.example.gotravel.data.api.model.Accommodation
import com.example.gotravel.data.api.model.ActivityItem
import com.example.gotravel.data.api.model.Attraction
import com.example.gotravel.data.api.model.DayActivity
import com.example.gotravel.data.api.model.FoodService
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.ui.components.CustomSearchBar
import com.example.gotravel.ui.custom_property.clickableWithScale
import com.example.gotravel.ui.module.user.schedule.ScheduleViewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable



@Composable
fun DraggableBottomSheet(
    onDismiss: () -> Unit = {},
    viewModel: ScheduleViewModel
) {
    val schedule = viewModel.schedule
    val isDragging = remember { mutableStateOf(false) }
    val groupedList = remember {
        mutableStateMapOf<String, MutableList<ActivityItem>>().apply {
            schedule?.activities?.forEach { dayActivity ->
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
                activity = items // items là List<Activity>
            )
        }
        val newSchedule = schedule?.copy(activities = newActivities)
        if (newSchedule != null) {
            println("Cập nhật Schedule với activities: ${newActivities.map { it.day to it.activity?.map { it.name } }}")
            viewModel.updateSchedule(newSchedule)
        }
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
            println("Kéo thả hoàn tất, gọi updateSchedule")
            updateSchedule()
        }
    }
    LaunchedEffect(reorderState.draggingItemIndex) {
        val dragging = reorderState.draggingItemIndex != null
        isDragging.value = dragging
        if (!dragging && flatList.value.isNotEmpty()) {
            println("Kéo thả hoàn tất, gọi updateSchedule")
            updateSchedule()
        }
    }

    Column (modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.8f)
        .background(Color.White)
        .padding(horizontal = 10.dp)
        .padding(bottom = 30.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Sắp xếp",
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f).padding(start = 35.dp)
            )
            Text(
                text = "Xong",
                color = Color.Gray,
                fontWeight = FontWeight.W400,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onDismiss() }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .reorderable(reorderState)
                .detectReorderAfterLongPress(reorderState),
            state = reorderState.listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(flatList.value, key = { _, pair ->
                val (group, item) = pair
                item?.let { "$group-$it" } ?: "header-$group"
            }) { _, (group, item) ->
                if (item == null) {
                    Text(
                        text = group,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp, vertical = 8.dp),
                        fontWeight = FontWeight.W700
                    )
                } else {
                    ReorderableItem(reorderState, key = "$group-$item") { isDragging ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .border(
                                    0.5.dp,
                                    if (!isDragging) Color.White else colorResource(id = R.color.darker_blue),
                                    RoundedCornerShape(6.dp)
                                ),
                            shape = RoundedCornerShape(6.dp),
                            elevation = CardDefaults.cardElevation(if (isDragging) 4.dp else 0.dp),
                            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.background)),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(end = 8.dp)
                            ) {
                                val activitiesForDay = when (val destination = item.destination) {
                                    is Attraction -> destination.attractionName ?: "Chưa có tên"
                                    is Accommodation -> destination.name ?: "Chưa có tên"
                                    is FoodService -> destination.foodServiceName ?: "Chưa có tên"
                                    else -> item.name ?: "Chưa có tên"
                                }
                                Text(
                                    text = activitiesForDay,
                                    fontWeight = FontWeight.W400,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 10.dp, vertical = 15.dp)
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_drag),
                                    contentDescription = null,
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}


