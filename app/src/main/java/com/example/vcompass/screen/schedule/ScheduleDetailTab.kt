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
import androidx.compose.foundation.gestures.animateScrollBy
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.R

import com.example.vcompass.helper.BottomSheetType
import com.example.vcompass.helper.CommonUtils.formatCurrency
import com.example.vcompass.util.clickableWithScale
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceWidth4
import com.example.vcompass.ui.core.space.SpaceWidth8
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypographyBold
import com.vcompass.presentation.model.business.accommodation.Accommodation
import com.vcompass.presentation.model.business.attraction.Attraction
import com.vcompass.presentation.model.business.foodplace.FoodPlace
import com.vcompass.presentation.model.schedule.Activity
import com.vcompass.presentation.model.schedule.Schedule
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ScheduleDetailTab(
    showBottomSheet: (BottomSheetType) -> Unit = {},
    schedule: Schedule? = null
) {
    val listState = rememberLazyListState()
    val selectedItem = remember { mutableStateOf(0) }
    val dayIndexToColumnIndex = remember(schedule) {
        val map = mutableMapOf<Int, Int>()
        var currentIndex = 0
        schedule?.days?.forEachIndexed { index, dayActivity ->
            map[dayActivity.day ?: (index + 1)] = currentIndex
            currentIndex++
            currentIndex += dayActivity.activity?.size ?: 0
        }
        map
    }

    LaunchedEffect(selectedItem.value) {
        listState.animateScrollBy(50f)
    }
    Column {
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
                        .padding(start = MyDimen.p10, end = MyDimen.p32)
                        .clip(RoundedCornerShape(MyDimen.p100))
                        .background(MyColor.Black)
                        .padding(MyDimen.p8)
                        .clickableWithScale { showBottomSheet(BottomSheetType.RANGE_DAY_PICKER) }
                ) {
                    CoreIcon(
                        resDrawable = R.drawable.ic_calendar,
                        tintColor = MyColor.White,
                        iconModifier = Modifier.size(MyDimen.p24)
                    )
                }
            }
            itemsIndexed(
                schedule?.days ?: listOf(),
                key = { index, item -> item.activity.toString() }) { index, item ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(MyDimen.p8))
                            .background(if (selectedItem.value != index) MyColor.White else MyColor.Gray999)
                            .padding(vertical = MyDimen.p12, horizontal = MyDimen.p20)
                            .clickableWithScale {
                                selectedItem.value = index
                            }
                    ) {
                        CoreText(
                            text = "Ngày " + item.day.toString(),
                            style = CoreTypographyBold.bodyMedium,
                            letterSpacing = 1.5.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp),
            state = listState
        ) {
            itemsIndexed(
                schedule?.days ?: listOf(),
                key = { index, item -> item.activity.toString() }) { index, item ->
                Text(
                    text = "Ngày ${item.day}: ${item.activity?.size} hoạt động",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.W700,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 5.dp)
                )
                item.activity?.forEachIndexed { index, item ->
                    ActivityCard(showBottomSheet, index, item)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ActivityCard(
    showBottomSheet: (BottomSheetType) -> Unit = {},
    index: Int,
    item: Activity,
    schedule: Schedule? = null,
) {
    val type = when (item.activityType) {
        "Attraction" -> "Tham quan"
        "Accommodation" -> "Nghỉ ngơi"
        "FoodService" -> "Ẩm thực"
        else -> "Khác"
    }
    val attraction = item.destination as? Attraction
    val accommodation = item.destination as? Accommodation
    val foodService = item.destination as? FoodPlace

    val operatingHours =
        attraction?.operatingHours?.firstOrNull() ?: foodService?.operatingHours?.firstOrNull()
    val price = foodService?.price?.minPrice ?: attraction?.price ?: 0
    val ratings = attraction?.totalRatings ?: foodService?.totalRatings ?: accommodation?.totalRatings

    val imageSrc = accommodation?.images?.firstOrNull()
        ?: attraction?.images?.firstOrNull()
        ?: foodService?.images?.firstOrNull()
        ?: item.imgSrc?.firstOrNull()

    var seeMoreExtension by rememberSaveable { mutableStateOf(false) }
    var isShowDetail by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var userNote by rememberSaveable { mutableStateOf(item.description) }
    val serviceList =
        (accommodation?.amenities ?: attraction?.amenities ?: foodService?.amenities
        ?: listOf())
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        snapshotFlow { userNote }
            .debounce(3000)
            .distinctUntilChanged()
            .collect { newUserNote ->
                val newSchedule = schedule?.copy(
                    days = schedule.days?.map { day ->
                        day.copy(
                            activity = day.activity?.map { activity ->
                                if (activity.id == item.id) {
                                    activity.copy(description = newUserNote)
                                } else activity
                            }
                        )
                    }
                )
                if (newSchedule != null) {
                    // viewModel.updateSchedule(newSchedule)
                }
            }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .background(
                color = if (!isShowDetail) Color.White else MyColor.GrayEEE,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
            .clickableWithScale { isShowDetail = !isShowDetail }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(28.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CoreIcon(
                        imageVector = Icons.Rounded.Place,
                        tintColor = MyColor.Purple,
                        iconModifier = Modifier.fillMaxSize()
                    )
                    Text(
                        text = (index + 1).toString(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.W600,
                        color = Color.White,
                        modifier = Modifier.offset(x = (-2).dp, y = (-2).dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = type,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        //viewModel.activityId = item.id.toString()
                        showBottomSheet(BottomSheetType.TIME_PICKER)
                    }
                    .background(
                        MyColor.GrayEEE,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 5.dp, vertical = 3.dp)
            ) {
                CoreIcon(
                    resDrawable = R.drawable.ic_clock_24dp,
                    tintColor = MyColor.DarkBlue,
                    iconModifier = Modifier.size(MyDimen.p24)
                )
                SpaceWidth4()
                Text(
                    text = item.timeStart + " - " + item.timeEnd,
                    fontSize = 15.sp,
                    color = MyColor.DarkBlue,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.ic_service_management),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.Top)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = (accommodation?.name ?: attraction?.name
                        ?: foodService?.name ?: item.name).toString(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W600,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!isShowDetail) {
                        Icon(
                            painter = painterResource(R.drawable.ic_favorite_star_solid),
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .size(15.dp)
                                .align(Alignment.Top)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                    BasicTextField(
                        value = userNote.toString(),
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
            //////


            //////
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
                    .background(
                        MyColor.GrayEEE,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 5.dp, vertical = 3.dp)
                    .clickable(
                        indication = null,
                        interactionSource = null
                    ) {
                        // viewModel.activityId = item.id.toString()
                        showBottomSheet(BottomSheetType.PRICE_PICKER)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_cost_24dp),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = formatCurrency(item.cost) + " đ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Red,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
    AnimatedVisibility(
        visible = isShowDetail,
        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            if (serviceList.isNotEmpty()) {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            4.dp,
                            Alignment.CenterVertically
                        ),
                        overflow = FlowRowOverflow.Clip,
                    ) {
                        serviceList.take(if (seeMoreExtension) 10 else 3).forEach { item ->
                            Row(
                                modifier = Modifier
                                    .background(
                                        MyColor.Gray999,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                            ) {
                                Text(
                                    text = item,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 13.sp,
                                    color = Color.DarkGray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    if (!seeMoreExtension) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable { seeMoreExtension = true }
                                .align(Alignment.CenterEnd)
                                .background(
                                    Color.White.copy(alpha = 0.8f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(vertical = 6.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                text = "Xem thêm",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W600,
                                color = Color.DarkGray,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                if (item.activityType != "Other") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CoreIcon(
                            imageVector = Icons.Rounded.Star,
                            tintColor = MyColor.Rating,
                            iconModifier = Modifier.size(MyDimen.p16)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = ratings.toString() + "★ (" + ratings.toString() + ")",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400,
                            color = MyColor.Rating,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoreIcon(
                        resDrawable = R.drawable.ic_information_24dp,
                        tintColor = MyColor.Primary,
                        iconModifier = Modifier
                            .size(15.dp)
                            .align(Alignment.Top)
                    )
                    SpaceWidth8()
                    Text(
                        text = accommodation?.description ?: attraction?.description
                        ?: foodService?.description ?: item.description.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        color = Color.DarkGray,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CoreIcon(
                        imageVector = Icons.Rounded.Place,
                        iconModifier = Modifier
                            .size(15.dp)
                            .align(Alignment.Top)
                    )
                    SpaceWidth8()
                    Text(
                        text = accommodation?.location?.address ?: attraction?.location?.address
                        ?: foodService?.location?.address ?: item.address.toString(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W400,
                        color = MyColor.DarkBlue,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                if (operatingHours != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CoreIcon(
                            resDrawable = R.drawable.ic_clock_24dp,
                            iconModifier = Modifier
                                .size(15.dp)
                                .align(Alignment.Top)
                        )
                        SpaceWidth8()
                        Text(
                            text = operatingHours?.openTime + " - " + operatingHours?.closeTime + " (" + operatingHours?.startDay + " - " + operatingHours?.endDay + ")",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.DarkGray,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))

                if (item.activityType == "FoodService" || item.activityType == "Attraction") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.ic_cost_24dp),
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .size(15.dp)
                                .align(Alignment.Top)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = formatCurrency(price) + " đ",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.DarkGray,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier
                            .background(
                                MyColor.DarkOrange,
                                shape = RoundedCornerShape(99.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                            .clickableWithScale {
                                var uri = "".toUri()
                                val lat = accommodation?.location?.latitude
                                    ?: attraction?.location?.latitude
                                    ?: foodService?.location?.latitude
                                val lng = accommodation?.location?.longitude
                                    ?: attraction?.location?.longitude
                                    ?: foodService?.location?.longitude
                                val label = accommodation?.location?.address
                                    ?: attraction?.location?.address
                                    ?: foodService?.location?.address

                                uri = if (lat == null || lng == null) {
                                    val address = item.address.toString()
                                    if (address == "") {
                                        return@clickableWithScale
                                    } else
                                        "geo:0,0?q=$address".toUri()
                                } else {
                                    "geo:$lat,$lng?q=$lat,$lng($label)".toUri()
                                }
                                // Chỉ đường
                                // val uri = Uri.parse("google.navigation:q=$lat,$lng")

                                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                                    setPackage("com.google.android.apps.maps")
                                }

                                if (intent.resolveActivity(context.packageManager) != null) {
                                    context.startActivity(intent)
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Đường đi",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.White,
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(R.drawable.ic_delete_24dp),
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(20.dp)
                            .clickableWithScale { showDialog = true })
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(R.drawable.ic_article_outline_24dp),
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
}

