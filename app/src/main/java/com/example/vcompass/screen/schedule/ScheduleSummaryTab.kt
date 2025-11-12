package com.example.vcompass.screen.schedule

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vcompass.R
import com.example.vcompass.helper.BottomSheetType
import com.example.vcompass.ui.core.divider.DividerOrientation
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.MoreOptionIcon
import com.example.vcompass.ui.core.text.BorderLessTextField
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.util.clickableWithScale
import com.example.vcompass.util.scaleOnClick
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight4
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographySemiBold
import com.vcompass.presentation.model.schedule.Comment
import com.vcompass.presentation.model.schedule.DayActivity
import com.vcompass.presentation.model.schedule.Schedule

@Preview(showSystemUi = true)
@Composable
fun ScheduleSummaryTab(
    showBottomSheet: (BottomSheetType) -> Unit = {},
    schedule: Schedule = Schedule()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SummaryInfoSection(schedule)
        DescriptionSection(schedule)
        schedule.days?.forEachIndexed { index, item ->
            SpaceHeight()
            SummaryDay(
                title = "Ngày ${index + 1}",
                showBottomSheet = { showBottomSheet(it) },
                activities = item
            )
        }
        SpaceHeight()
        PriceSection(schedule)
    }
}

@Composable
fun SummaryInfoSection(
    schedule: Schedule?
) {
    var isLiked by rememberSaveable { mutableStateOf(false) }

    fun countComments(comments: List<Comment>?): Int {
        if (comments.isNullOrEmpty()) return 0
        return comments.sumOf { comment ->
            1 + (comment.replies?.size ?: 0)
        }
    }

    fun countActivitiesByType(type: String): Int {
        var count = 0
        schedule?.days?.forEach { activityDay ->
            activityDay.activity?.forEach { activity ->
                if (activity.activityType == type) {
                    count++
                }
            }
        }
        return count
    }
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
                resDrawable = R.drawable.ic_heart_solid,
                tintColor = if (isLiked) {
                    MyColor.Red
                } else {
                    MyColor.Gray666
                },
                iconModifier = Modifier
                    .size(MyDimen.p24)
                    .scaleOnClick {
                        isLiked = !isLiked
                    }
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
            resIcon = R.drawable.ic_comment_fill,
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
            modifier = Modifier.height(MyDimen.p20)
        )
        ScheduleSummaryInforItem(
            modifier = Modifier.weight(1f),
            resIcon = R.drawable.ic_beach_24dp,
            count = countActivitiesByType("Attraction")
        )
        VerticalDivider(
            thickness = 0.5.dp, color = Color.LightGray,
            modifier = Modifier.height(MyDimen.p20)
        )
        ScheduleSummaryInforItem(
            modifier = Modifier.weight(1f),
            resIcon = R.drawable.ic_notification,
            count = countActivitiesByType("Other")
        )
        SpaceHeight()
    }
}

@Composable
fun DescriptionSection(
    schedule: Schedule?
) {
    var descriptionTitle by remember { mutableStateOf("Mô tả") }
    var description by remember { mutableStateOf(schedule?.description ?: "Mô tả") }
    var isExpanded by rememberSaveable { mutableStateOf(true) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(vertical = MyDimen.p6),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoreIcon(
                imageVector = if (!isExpanded) Icons.AutoMirrored.Rounded.KeyboardArrowRight
                else Icons.Rounded.KeyboardArrowDown,
                iconModifier = Modifier.size(MyDimen.p24),
                boxModifier = Modifier.padding(start = MyDimen.p8)
            )
            BorderLessTextField(
                modifier = Modifier.weight(1f),
                value = descriptionTitle,
                onValueChange = { descriptionTitle = it },
                textStyle = CoreTypographyBold.bodyMedium,
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
    title: String = "",
    activities: DayActivity
) {
    var isExpanded by rememberSaveable { mutableStateOf(true) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = MyDimen.p6),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoreIcon(
                imageVector = if (!isExpanded) Icons.AutoMirrored.Rounded.KeyboardArrowRight
                else Icons.Rounded.KeyboardArrowDown,
                iconModifier = Modifier.size(MyDimen.p24),
                boxModifier = Modifier.padding(start = MyDimen.p8)
            )
            BorderLessTextField(
                modifier = Modifier.weight(1f),
                value = title,
                textStyle = CoreTypographyBold.bodyMedium,
                enabled = false
            )
            MoreOptionIcon()
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(animationSpec = tween(500)) + fadeIn(),
            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().heightIn(max = MyDimen.p5000),
                    verticalArrangement = Arrangement.spacedBy(MyDimen.p6)
                ) {
                    itemsIndexed(
                        activities.activity ?: listOf(),
                        key = { index, item -> item.id.toString() }) { index, item ->
                        ActivityCard(showBottomSheet, index, item)
                    }
                }

                Row(
                    modifier = Modifier.padding(horizontal = MyDimen.p16, vertical = MyDimen.p6),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(MyDimen.p8))
                            .clickableWithScale { showBottomSheet(BottomSheetType.ADD_ACTIVITY) }
                            .background(MyColor.Gray999)
                            .weight(1f)
                            .padding(MyDimen.p10)
                    ) {
                        CoreIcon(
                            imageVector = Icons.Rounded.Place,
                            iconModifier = Modifier.size(MyDimen.p20)
                        )
                        Spacer(modifier = Modifier.width(MyDimen.p6))
                        CoreText(
                            text = "Thêm hoạt động mới",
                            style = CoreTypography.labelMedium,
                            color = Color.Gray,
                        )
                    }
                    SpaceWidth8()
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(MyDimen.p100))
                            .background(MyColor.Gray999)
                            .padding(MyDimen.p8)
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