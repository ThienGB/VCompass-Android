package com.example.vcompass.screen.schedule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.accessed.core.compose_view.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.data.api.model.Comment
import com.example.vcompass.data.api.model.DayActivity
import com.example.vcompass.data.api.model.Schedule
import com.example.vcompass.helper.BottomSheetType
import com.example.vcompass.ui.core.divider.DividerOrientation
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.MoreOptionIcon
import com.example.vcompass.util.clickableWithScale
import com.example.vcompass.util.scaleOnClick
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.compose_view.space.SpaceHeight
import com.vcompass.core.compose_view.space.SpaceHeight4
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography
import com.vcompass.core.typography.CoreTypographySemiBold
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun ScheduleSummaryTab(
    showBottomSheet: (BottomSheetType) -> Unit = {},
    schedule: Schedule?
) {
    var description by remember { mutableStateOf(schedule?.description ?: "") }
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    var iconLike by remember { mutableStateOf(R.drawable.ic_heart) }
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()
    fun onLikeClick() {
        iconLike = if (iconLike == R.drawable.ic_heart_solid) {
            R.drawable.ic_heart
        } else {
            R.drawable.ic_heart_solid
        }
        coroutineScope.launch {
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 150)
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 150)
            )
        }
    }

    fun countComments(comments: List<Comment>?): Int {
        if (comments.isNullOrEmpty()) return 0
        return comments.sumOf { comment ->
            1 + (comment.replies?.size ?: 0)
        }
    }

    fun countActivitiesByType(type: String): Int {
        var count = 0
        schedule?.activities?.forEach { activityDay ->
            activityDay.activity?.forEach { activity ->
                if (activity.activityType == type) {
                    count++
                }
            }
        }
        return count
    }

    LaunchedEffect(Unit) {
        snapshotFlow { description }
            .debounce(3000) // 3 giây
            .distinctUntilChanged()
            .collect { newDescription ->
                val newSchedule = schedule?.copy(description = newDescription)
                //viewModel.updateSchedule(newSchedule)
            }
    }
    Column {
        SpaceHeight(MyDimen.p10)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MyDimen.p12),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p10)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoreIcon(
                    resDrawable = iconLike,
                    tintColor = if (iconLike == R.drawable.ic_heart_solid) {
                        Color.Red
                    } else {
                        Color.Black
                    },
                    iconModifier = Modifier.scaleOnClick { onLikeClick() }
                )
                SpaceHeight4()
                CoreText(
                    text = schedule?.likes?.size.toString(),
                    style = CoreTypographySemiBold.displaySmall
                )
            }
            ItemDivider(
                orientation = DividerOrientation.Vertical,
                modifier = Modifier.height(MyDimen.p20)
            )
            ScheduleSummaryInforItem(
                modifier = Modifier.weight(1f),
                resIcon = R.drawable.ic_comment_dots,
                count = countComments(schedule?.comments)
            )
            ItemDivider(
                orientation = DividerOrientation.Vertical,
                modifier = Modifier.height(MyDimen.p20)
            )
            ScheduleSummaryInforItem(
                modifier = Modifier.weight(1f),
                resIcon = R.drawable.ic_accommodation_24dp,
                count = countActivitiesByType("Accommodation")
            )
            ItemDivider(
                orientation = DividerOrientation.Vertical,
                modifier = Modifier.height(MyDimen.p20)
            )
            ScheduleSummaryInforItem(
                modifier = Modifier.weight(1f),
                resIcon = R.drawable.ic_food,
                count = countActivitiesByType("FoodService")
            )
            VerticalDivider(
                thickness = 0.5.dp, color = Color.LightGray,
                modifier = Modifier.height(20.dp)
            )
            ScheduleSummaryInforItem(
                modifier = Modifier.weight(1f),
                resIcon = R.drawable.ic_beach_24dp,
                count = countActivitiesByType("Attraction")
            )
            VerticalDivider(
                thickness = 0.5.dp, color = Color.LightGray,
                modifier = Modifier.height(20.dp)
            )
            ScheduleSummaryInforItem(
                modifier = Modifier.weight(1f),
                resIcon = R.drawable.ic_notification,
                count = countActivitiesByType("Other")
            )
            SpaceHeight()
        }
        Column(modifier = Modifier.background(Color.White)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoreIcon(
                    imageVector = if (!isExpanded) Icons.AutoMirrored.Rounded.KeyboardArrowRight
                    else Icons.AutoMirrored.Rounded.ArrowForward,
                    iconModifier = Modifier.size(MyDimen.p20)
                )
                TextField(
                    value = "Mô tả",
                    onValueChange = {},
                    enabled = false,
                    textStyle = TextStyle(
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp,
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent, // Nền trong suốt khi chọn
                        unfocusedContainerColor = Color.Transparent, // Nền trong suốt khi không chọn
                        disabledContainerColor = Color.Transparent, // Nền trong suốt khi bị vô hiệu hóa
                        focusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi chọn
                        unfocusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi không chọn
                        disabledIndicatorColor = Color.Transparent,
                        disabledTextColor = Color.Black,

                        ),
                    modifier = Modifier
                        .weight(1f) // Căn lề theo chiều rộng
                        .padding(horizontal = 0.dp, vertical = 0.dp) // Giảm padding
                )
                MoreOptionIcon()
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
            ) {
                TextField(
                    value = description.toString(),
                    onValueChange = { description = it },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MyColor.GrayEEE,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .offset(y = (-10).dp)
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp, vertical = 0.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .heightIn(min = 70.dp),
                    singleLine = false,
                    minLines = 2,
                    maxLines = 4
                )
            }
        }
        schedule?.activities?.forEachIndexed { index, item ->
            Spacer(modifier = Modifier.height(18.dp))
            SummaryDay(
                items = "Ngày ${index + 1}",
                showBottomSheet = { showBottomSheet(it) },
                activities = item
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
        PriceSection(schedule)
    }
}

@Composable
fun ScheduleSummaryInforItem(
    modifier: Modifier = Modifier,
    resIcon: Int,
    count: Int
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CoreIcon(
            resDrawable = resIcon,
            iconModifier = Modifier.size(MyDimen.p24)
        )
        SpaceHeight4()
        CoreText(
            text = count.toString(),
            style = CoreTypography.displaySmall,
            color = MyColor.TextColorLight
        )
    }
}

@Composable
fun SummaryDay(
    showBottomSheet: (BottomSheetType) -> Unit = {},
    items: String = "",
    activities: DayActivity
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            CoreIcon(
                imageVector = if (!isExpanded) Icons.AutoMirrored.Rounded.KeyboardArrowRight
                else Icons.AutoMirrored.Rounded.ArrowForward,
                iconModifier = Modifier.size(MyDimen.p20)
            )
            TextField(
                value = items,
                onValueChange = {},
                enabled = false,
                textStyle = TextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 20.sp,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent, // Nền trong suốt khi không chọn
                    disabledContainerColor = Color.Transparent, // Nền trong suốt khi bị vô hiệu hóa
                    focusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi chọn
                    unfocusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi không chọn
                    disabledIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Black,
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 0.dp, vertical = 0.dp) // Giảm padding
            )
            MoreOptionIcon()
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(animationSpec = tween(500)) + fadeIn(),
            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
        ) {
            Column(modifier = Modifier.background(Color.White)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 3.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    itemsIndexed(
                        activities.activity!!,
                        key = { index, item -> item.id.toString() }) { index, item ->
                        ActivityCard(showBottomSheet, index, item)
                    }

                }

                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickableWithScale { showBottomSheet(BottomSheetType.ADD_ACTIVITY) }
                            .background(MyColor.Gray999)
                            .weight(1f)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        CoreIcon(
                            imageVector = Icons.Rounded.Place,
                            iconModifier = Modifier.size(MyDimen.p20)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Thêm hoạt động mới",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.Gray,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(99.dp))
                            .background(MyColor.Gray999)
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                            .clickableWithScale { showBottomSheet(BottomSheetType.DRAG_ITEM) }
                    ) {
                        CoreIcon(
                            resDrawable = R.drawable.ic_drag_indicator_24dp,
                            iconModifier = Modifier.size(MyDimen.p20)
                        )
                    }
                }
            }


        }
    }
}