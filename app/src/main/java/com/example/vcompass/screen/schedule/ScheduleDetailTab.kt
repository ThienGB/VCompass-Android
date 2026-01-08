package com.example.vcompass.screen.schedule

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StickyNote2
import androidx.compose.material.icons.rounded.AccessTimeFilled
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.vcompass.R
import com.example.vcompass.enum.ActivityTypeEnum
import com.example.vcompass.helper.CommonUtils.formatCurrency
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographyMedium
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.divider.ItemDivider
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceHeight4
import com.example.vcompass.ui.core.space.SpaceHeight8
import com.example.vcompass.ui.core.space.SpaceWidth
import com.example.vcompass.ui.core.space.SpaceWidth4
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.util.clickableWithScale
import com.vcompass.presentation.enums.BottomSheetType
import com.vcompass.presentation.model.schedule.Activity
import com.vcompass.presentation.model.schedule.Schedule
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScheduleDetailTab(
    schedule: Schedule
) {
    val bringIntoViewRequesters = remember {
        List(schedule.days?.size ?: 0) { BringIntoViewRequester() }
    }
    val selectedItem = remember { mutableStateOf(0) }
    LaunchedEffect(selectedItem.value) {
        bringIntoViewRequesters[selectedItem.value].bringIntoView()
    }
    Column {
        SpaceHeight()
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(MyDimen.p10),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(horizontal = MyDimen.p16)
                        .clip(CircleShape)
                        .background(MyColor.Gray333)
                        .padding(MyDimen.p8)
                        .clickableWithScale { }
                ) {
                    CoreIcon(
                        resDrawable = R.drawable.ic_calendar,
                        tintColor = MyColor.White,
                        iconModifier = Modifier.size(MyDimen.p24)
                    )
                }
            }
            itemsIndexed(schedule.days ?: listOf()) { index, item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(MyDimen.p8))
                            .background(if (selectedItem.value != index) MyColor.GrayEEE else MyColor.SecondPrimary)
                            .padding(vertical = MyDimen.p10, horizontal = MyDimen.p16)
                            .clickableWithScale {
                                selectedItem.value = index
                            }
                    ) {
                        CoreText(
                            text = stringResource(R.string.lb_day) + " " + item.day.toString(),
                            style = CoreTypographySemiBold.labelLarge,
                            textAlign = TextAlign.Center,
                            color = if (selectedItem.value != index) MyColor.TextColorPrimary else MyColor.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = MyDimen.maxScrollHeight),
            userScrollEnabled = false
        ) {
            itemsIndexed(schedule.days ?: listOf()) { day, item ->
                Column(
                    modifier = Modifier.bringIntoViewRequester(bringIntoViewRequesters[day])
                ) {
                    CoreText(
                        text = stringResource(R.string.lb_day) + " ${item.day}: ${item.activities?.size} " + if (item.activities?.size == 1)
                            stringResource(R.string.lb_activity) else stringResource(R.string.lb_activities),
                        style = CoreTypographyBold.bodyMedium,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(MyDimen.p16)
                    )
                    item.activities?.forEachIndexed { index, item ->
                        ActivityCard(index, day, item)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActivityCard(
    index: Int,
    day: Int,
    activity: Activity = Activity(),
    viewModel: ScheduleViewModel = koinViewModel()
) {
    val business = activity.business
    val type = ActivityTypeEnum.getType(activity.activityType)
    val operatingHours = business.operatingHours?.firstOrNull()
    val imageSrc = business.images?.firstOrNull()

    var seeMoreExtension by rememberSaveable { mutableStateOf(false) }
    var isShowDetail by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var userNote by rememberSaveable { mutableStateOf(activity.description) }
    val serviceList = business.amenities
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MyDimen.p8)
            .background(
                color = if (!isShowDetail) MyColor.White else MyColor.GrayEEE,
                shape = RoundedCornerShape(MyDimen.p8)
            )
            .padding(horizontal = MyDimen.p8, vertical = MyDimen.p16)
            .clickableWithScale { isShowDetail = !isShowDetail }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(28.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CoreIcon(
                        resDrawable = R.drawable.ic_place_fill_24dp,
                        tintColor = MyColor.DarkBlue,
                        iconModifier = Modifier.fillMaxSize(),
                        boxModifier = Modifier.offset(x = (-2).dp)
                    )
                    CoreText(
                        text = (index + 1).toString(),
                        style = CoreTypographyBold.bodySmall,
                        color = MyColor.White,
                        modifier = Modifier.offset(x = (-4.5).dp, y = (-3).dp)
                    )
                }
                SpaceWidth4()
                CoreText(
                    text = stringResource(type.titleRes),
                    style = CoreTypographyBold.displayLarge,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(MyDimen.p8))
                    .background(MyColor.Primary.copy(0.05f))
                    .clickable {
                        viewModel.currentDay = day
                        viewModel.currentActivity = activity
                        viewModel.setSheetType(BottomSheetType.ADD_TIME)
                    }
                    .padding(horizontal = MyDimen.p6, vertical = MyDimen.p2),
            ) {
                CoreIcon(
                    imageVector = Icons.Rounded.AccessTimeFilled,
                    tintColor = MyColor.Primary,
                    iconModifier = Modifier.size(MyDimen.p24)
                )
                SpaceWidth4()
                CoreText(
                    text = activity.timeStart + " - " + activity.timeEnd,
                    style = CoreTypographyMedium.labelLarge,
                    color = MyColor.Primary,
                    textAlign = TextAlign.Center
                )
            }
        }
        SpaceHeight8()
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoreIcon(
                        resDrawable = type.iconRes,
                        iconModifier = Modifier.size(MyDimen.p20)
                    )
                    SpaceWidth8()
                    CoreText(
                        text = business.name,
                        style = CoreTypographySemiBold.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                SpaceHeight4()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!isShowDetail) {
                        CoreIcon(
                            imageVector = Icons.AutoMirrored.Rounded.StickyNote2,
                            iconModifier = Modifier.size(MyDimen.p20),
                            boxModifier = Modifier.align(Alignment.Top)
                        )
                        SpaceWidth8()
                    }
                    BasicTextField(
                        value = userNote,
                        onValueChange = { userNote = it },
                        textStyle = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400,
                            color = Color.DarkGray
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        enabled = isShowDetail,
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = if (!isShowDetail) 4 else 15,
                        decorationBox = { innerTextField ->
                            innerTextField()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(5.dp))

            Image(
                painter = rememberAsyncImagePainter(
                    model = imageSrc,
                    error = painterResource(R.drawable.ic_king_bed_24dp)
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(MyDimen.p8))
                    .background(MyColor.Red.copy(0.05f))
                    .clickable {
                        viewModel.currentDay = day
                        viewModel.currentActivity = activity
                        viewModel.setSheetType(BottomSheetType.ADD_COST)
                    }
                    .padding(horizontal = MyDimen.p6, vertical = MyDimen.p2),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoreIcon(
                    resDrawable = R.drawable.ic_cost_24dp,
                    tintColor = Color.Red,
                    iconModifier = Modifier.size(MyDimen.p20)
                )
                SpaceWidth8()
                CoreText(
                    text = formatCurrency(activity.cost) + " đ",
                    style = CoreTypographyMedium.labelLarge,
                    color = Color.Red,
                )
            }
        }
    }
    SpaceHeight8()
    AnimatedVisibility(
        visible = isShowDetail,
        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MyDimen.p16)
        ) {
            if (serviceList?.isNotEmpty() == true) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(MyDimen.p6),
                        verticalArrangement = Arrangement.spacedBy(MyDimen.p4),
                        overflow = FlowRowOverflow.Clip,
                    ) {
                        serviceList.take(if (seeMoreExtension) 10 else 3)
                            .forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .background(
                                            MyColor.GrayF5,
                                            shape = RoundedCornerShape(MyDimen.p6)
                                        )
                                        .padding(horizontal = MyDimen.p10, vertical = MyDimen.p6)
                                ) {
                                    CoreText(
                                        text = item,
                                        style = CoreTypographySemiBold.labelSmall,
                                        color = MyColor.TextColorGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                    }
                    if (!seeMoreExtension || serviceList.size > 3) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { seeMoreExtension = true }
                                .align(Alignment.CenterEnd)
                                .background(
                                    Color.White.copy(alpha = 0.8f),
                                    shape = RoundedCornerShape(MyDimen.p6)
                                )
                                .padding(vertical = MyDimen.p6, horizontal = MyDimen.p8)
                        ) {
                            CoreText(
                                text = "Xem thêm",
                                style = CoreTypographyMedium.bodySmall,
                                color = MyColor.TextColorGray,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                SpaceHeight8()
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                if (type != ActivityTypeEnum.OTHER) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CoreIcon(
                            imageVector = Icons.Rounded.Star,
                            tintColor = MyColor.Rating,
                            iconModifier = Modifier.size(MyDimen.p20)
                        )
                        SpaceWidth8()
                        CoreText(
                            text = "${business.averageRating}★ (${business.totalRatings})",
                            style = CoreTypography.labelSmall,
                            color = MyColor.Rating,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    SpaceWidth8()
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoreIcon(
                        imageVector = Icons.Rounded.Info,
                        tintColor = MyColor.Primary,
                        iconModifier = Modifier.size(MyDimen.p20)
                    )
                    SpaceWidth8()
                    CoreText(
                        text = activity.business.description.toString(),
                        style = CoreTypography.labelSmall,
                        color = MyColor.TextColorLight,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                SpaceHeight8()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoreIcon(
                        imageVector = Icons.Rounded.Place,
                        iconModifier = Modifier.size(MyDimen.p20)
                    )
                    SpaceWidth8()
                    CoreText(
                        text = activity.business.location?.address.toString(),
                        style = CoreTypography.labelSmall,
                        color = MyColor.DarkBlue,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                SpaceHeight8()
                if (operatingHours != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CoreIcon(
                            imageVector = Icons.Rounded.AccessTimeFilled,
                            iconModifier = Modifier.size(MyDimen.p20)
                        )
                        SpaceWidth8()
                        CoreText(
                            text = operatingHours.openTime + " - " + operatingHours.closeTime + " (" + operatingHours?.startDay + " - " + operatingHours?.endDay + ")",
                            style = CoreTypography.labelSmall,
                            color = MyColor.TextColorLight,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                SpaceHeight()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier
                            .background(
                                MyColor.DarkOrange,
                                shape = RoundedCornerShape(MyDimen.p100)
                            )
                            .padding(horizontal = MyDimen.p10, vertical = MyDimen.p8)
                            .clickableWithScale {
                                var uri = "".toUri()
                                val lat = business.location?.coordinates?.get(0) ?: 0
                                val lng = business.location?.coordinates?.get(1) ?: 0
                                val label = business.location?.address

                                uri = "geo:$lat,$lng?q=$lat,$lng($label)".toUri()

                                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                                    setPackage("com.google.android.apps.maps")
                                }

                                if (intent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(intent)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoreText(
                            text = "Đường đi",
                            style = CoreTypographySemiBold.labelLarge,
                            color = Color.White,
                        )
                    }
                    ExpandableSpacer()
                    CoreIcon(
                        imageVector = Icons.Rounded.Delete,
                        iconModifier = Modifier.size(MyDimen.p24)
                    )
                    SpaceWidth()
                    CoreIcon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        iconModifier = Modifier.size(MyDimen.p24)
                    )
                }
                SpaceHeight()
            }
        }
    }
    ItemDivider()
}

