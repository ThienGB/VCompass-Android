//package com.example.vcompass.ui.module.user.schedule
//
//import RequestNotificationPermission
//import android.content.Intent
//import android.os.Build
//import android.util.Log
//import android.widget.Toast
//import androidx.activity.compose.LocalActivity
//import androidx.annotation.RequiresApi
//import androidx.compose.animation.AnimatedContent
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.expandVertically
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.shrinkVertically
//import androidx.compose.animation.slideInHorizontally
//import androidx.compose.animation.slideOutHorizontally
//import androidx.compose.animation.togetherWith
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.ScrollState
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.gestures.animateScrollBy
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.FlowRow
//import androidx.compose.foundation.layout.FlowRowOverflow
//import androidx.compose.foundation.layout.IntrinsicSize
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.heightIn
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
//import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.VerticalDivider
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.snapshotFlow
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.ColorFilter
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.layout.onGloballyPositioned
//import androidx.compose.ui.layout.positionInWindow
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalDensity
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.zIndex
//import androidx.lifecycle.viewmodel.compose.viewModel
//import coil.compose.rememberAsyncImagePainter
//import com.example.vcompass.R
//import com.example.vcompass.data.api.model.Accommodation
//import com.example.vcompass.data.api.model.ActivityItem
//import com.example.vcompass.data.api.model.Attraction
//import com.example.vcompass.data.api.model.Comment
//import com.example.vcompass.data.api.model.DayActivity
//import com.example.vcompass.data.api.model.FoodService
//import com.example.vcompass.data.api.model.Rating
//import com.example.vcompass.data.api.model.Schedule
//import com.example.vcompass.helper.BottomSheetType
//import com.example.vcompass.helper.CommonUtils.formatCurrency
//import com.example.vcompass.ui.components.Loading
//import com.example.vcompass.ui.components.sheet.AddActivityBottomSheet
//import com.example.vcompass.ui.components.sheet.CommentBottomSheet
//import com.example.vcompass.ui.components.sheet.CustomBottomSheet
//import com.example.vcompass.ui.components.sheet.DayPickerBottomSheet
//import com.example.vcompass.ui.components.sheet.DraggableBottomSheet
//import com.example.vcompass.ui.components.sheet.PriceBottomSheet
//import com.example.vcompass.ui.components.sheet.TimePickerBottomSheet
//import com.example.vcompass.ui.custom_property.clickableWithScale
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.GoogleMap
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState
//import com.google.maps.android.compose.Polyline
//import com.google.maps.android.compose.rememberCameraPositionState
//import kotlinx.coroutines.FlowPreview
//import kotlinx.coroutines.flow.debounce
//import kotlinx.coroutines.flow.distinctUntilChanged
//import kotlinx.coroutines.launch
//import kotlin.math.max
//import androidx.core.net.toUri
//
//@Composable
//fun Schedule(
//    viewModel: ScheduleViewModel
//){
//    LaunchedEffect(1) {
//        viewModel.fetchSchedule("1")
//    }
//    val schedule = viewModel.schedule
//    LaunchedEffect(schedule) {
//        print("Updated")
//    }
//    val isScheduleLoading = viewModel.isScheduleLoading
//
//    val bottomSheetState = remember { mutableStateOf(BottomSheetType.NONE) }
//    fun showBottomSheet(sheet: BottomSheetType) {
//        if (bottomSheetState.value != sheet) {
//            bottomSheetState.value = sheet
//        }
//    }
//    if (isScheduleLoading) {
//        Loading()
//    } else {
//        schedule?.let {
//            RequestNotificationPermission()
//            Box(modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//            ) {
//
//                Column(modifier = Modifier.background(Color.White)){
//                    ScheduleHeader(viewModel, showBottomSheet = { showBottomSheet(it) }, schedule)
//                }
//                if (bottomSheetState.value != BottomSheetType.NONE) {
//                    Box(modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.Gray.copy(alpha = 0.4f))
//                        .zIndex(1f))
//                }
//                Box(modifier = Modifier
//                    .zIndex(2f)
//                    .then(
//                        if (bottomSheetState.value == BottomSheetType.COMMENT
//                            || bottomSheetState.value == BottomSheetType.ADD_ACTIVITY
//                        )
//                            Modifier.fillMaxHeight(0.94f) else Modifier
//                    )
//                    .align(Alignment.BottomEnd)
//                ) {
//                    CustomBottomSheet(
//                        bottomSheetState = bottomSheetState,
//                        {bottomSheetState.value = BottomSheetType.NONE},
//                        bottomSheetContents = mapOf(
//                            BottomSheetType.TIME_PICKER to {
//                                TimePickerBottomSheet(onDismiss = {bottomSheetState.value = BottomSheetType.NONE}, viewModel)
//                            },
//                            BottomSheetType.RANGE_DAY_PICKER to {
//                                DayPickerBottomSheet(onDismiss = {bottomSheetState.value = BottomSheetType.NONE}, viewModel)
//                            },
//                            BottomSheetType.COMMENT to {
//                                CommentBottomSheet(viewModel)
//                            },
//                            BottomSheetType.PRICE_PICKER to {
//                                PriceBottomSheet(onDismiss = {bottomSheetState.value = BottomSheetType.NONE}, viewModel)
//                            },
//                            BottomSheetType.ADD_ACTIVITY to {
//                                AddActivityBottomSheet()
//                            },
//                            BottomSheetType.DRAG_ITEM to {
//                                DraggableBottomSheet(onDismiss = {bottomSheetState.value = BottomSheetType.NONE}, viewModel)
//                            },
//                        )
//                    )
//                }
//
//            }
//        }
//    }
//}
//
//@OptIn(FlowPreview::class)
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun ScheduleHeader(
//    viewModel: ScheduleViewModel,
//    showBottomSheet: (BottomSheetType) -> Unit,
//    schedule: Schedule
//){
//    val activeScheduleId = viewModel.activeScheduleId.collectAsState()
//    val scrollState = rememberScrollState()
//    val alpha by animateFloatAsState(
//        targetValue = when {
//            scrollState.value < 150 -> 1f
//            scrollState.value in 150..300 -> 1f - ((scrollState.value - 150) / 100f)
//            else -> 0f
//        }, label = "Box Alpha"
//    )
//    var scheduleName by remember { mutableStateOf(schedule.scheduleName) }
//    val activity = LocalActivity.current
//    val context = LocalContext.current
//
//    LaunchedEffect(Unit) {
//        snapshotFlow { scheduleName }
//            .debounce(3000) // 3 giây
//            .distinctUntilChanged()
//            .collect { newName ->
//                val newSchedule = schedule.copy(scheduleName = newName)
//                viewModel.updateSchedule(newSchedule)
//            }
//    }
//
//    Column (modifier = Modifier
//        .verticalScroll(scrollState)
//        .background(Color.White)){
//        Box (modifier = Modifier
//            .align(Alignment.CenterHorizontally)
//            .graphicsLayer { this.alpha = alpha }
//            .zIndex(1f)
//        ) {
//            Image(
//                painter = painterResource(R.drawable.img_ha_noi),
//                contentScale = ContentScale.Crop,
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//            )
//            Column {
//                Row (horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 10.dp, vertical = 5.dp)
//                ) {
//                    Column (modifier = Modifier
//                        .weight(1f)
//                        .clickable { activity?.finish() }) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_home_black_24dp),
//                            contentDescription = null,
//                            tint = Color.White,
//                            modifier = Modifier.size(25.dp)
//                        )
//                    }
//                    Row (verticalAlignment = Alignment.CenterVertically) {
//                        Box(
//                            modifier = Modifier
//                                .size(35.dp)
//                                .background(
//                                    color = Color.White,
//                                    shape = CircleShape
//                                )
//                                .border(
//                                    width = 1.dp,
//                                    color = Color.LightGray,
//                                    shape = CircleShape
//                                )
//                                .padding(2.dp)
//                        ) {
//                            Image(
//                                painter = painterResource(R.drawable.img_hue),
//                                contentScale = ContentScale.Crop,
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .clip(CircleShape)
//                            )
//                        }
//                    }
//                    Row (verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.offset(x = (-10).dp)) {
//                        Box(
//                            modifier = Modifier
//                                .size(35.dp)
//                                .background(
//                                    color = Color.White,
//                                    shape = CircleShape
//                                )
//                                .border(
//                                    width = 1.dp,
//                                    color = Color.LightGray,
//                                    shape = CircleShape
//                                )
//                                .padding(2.dp)
//                        ) {
//                            Image(
//                                painter = painterResource(R.drawable.ic_add_people),
//                                contentScale = ContentScale.Crop,
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .clip(CircleShape)
//                            )
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//                Column {
//                    Surface(
//                        shape = RoundedCornerShape(8.dp),
//                        shadowElevation = 12.dp,
//                        color = Color.White,
//                        modifier = Modifier
//                            .padding(horizontal = 13.dp)
//                            .fillMaxWidth()
//                            .zIndex(2f)
//                    ) {
//                        Row (modifier = Modifier.padding(bottom = 10.dp),
//                            horizontalArrangement = Arrangement.SpaceAround) {
//                            Column (modifier = Modifier
//                                .weight(1f)
//                                .fillMaxWidth()) {
//                                TextField(
//                                    value = scheduleName.toString(),
//                                    onValueChange = {scheduleName = it},
//                                    textStyle = TextStyle(
//                                        fontWeight = FontWeight.W700,
//                                        fontSize = 20.sp,
//                                        textAlign = TextAlign.Center
//                                    ),
//                                    colors = TextFieldDefaults.colors(
//                                        focusedContainerColor = Color.Transparent,
//                                        unfocusedContainerColor = Color.Transparent,
//                                        disabledContainerColor = Color.Transparent,
//                                        focusedIndicatorColor = Color.Transparent,
//                                        unfocusedIndicatorColor = Color.Transparent,
//                                        disabledIndicatorColor = Color.Transparent,
//                                        disabledTextColor = Color.Black
//                                    ),
//                                    minLines = 2,
//                                    maxLines = 2,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(IntrinsicSize.Min),
//                                )
//                                Row (modifier = Modifier.padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically) {
//                                    Column (modifier = Modifier.clickable { showBottomSheet(BottomSheetType.RANGE_DAY_PICKER) }) {
//                                        Row (verticalAlignment = Alignment.CenterVertically) {
//                                            Icon(
//                                                painter = painterResource(R.drawable.ic_calendar),
//                                                contentDescription = null,
//                                                tint = colorResource(id = R.color.background_gray_color) ,
//                                                modifier = Modifier.size(20.dp)
//                                            )
//                                            Spacer(modifier = Modifier.width(6.dp))
//                                            Text(
//                                                text = schedule.dateStart.toString() + " - " + schedule.dateEnd.toString(),
//                                                fontSize = 15.sp,
//                                                fontWeight = FontWeight.W600,
//                                                color = colorResource(id = R.color.background_gray_color),
//                                            )
//                                        }
//                                        Spacer(modifier = Modifier.height(2.dp))
//                                        Row (verticalAlignment = Alignment.CenterVertically) {
//                                            Icon(
//                                                painter = painterResource(R.drawable.ic_moon),
//                                                contentDescription = null,
//                                                tint = colorResource(id = R.color.background_gray_color) ,
//                                                modifier = Modifier.size(20.dp)
//                                            )
//                                            Spacer(modifier = Modifier.width(6.dp))
//                                            Text(
//                                                text = schedule.numDays.toString() + " ngày " + (schedule.numDays - 1) + " đêm",
//                                                fontSize = 15.sp,
//                                                fontWeight = FontWeight.W600,
//                                                color = colorResource(id = R.color.background_gray_color),
//                                            )
//                                        }
//                                    }
//                                    Spacer(modifier = Modifier.weight(1f))
//                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
//                                        modifier = Modifier
//                                            .background(
//                                                colorResource(id = if (activeScheduleId.value != schedule.id.toString())  R.color.blue
//                                                else R.color.colorOrange),
//                                                shape = RoundedCornerShape(99.dp)
//                                            )
//                                            .padding(vertical = 6.dp, horizontal = 10.dp)
//                                            .clickableWithScale{
//                                                if (activeScheduleId.value == schedule.id.toString()){
//                                                    viewModel.stopSchedule(context)
//                                                    Toast.makeText(context,"Đã dừng lịch trình ${schedule.scheduleName}", Toast.LENGTH_SHORT).show()
//                                                }else {
//                                                    viewModel.startSchedule(schedule.id.toString(), context)
//                                                    Toast.makeText(context, "Bắt đầu theo dõi lịch trình: ${schedule.scheduleName}", Toast.LENGTH_SHORT).show()
//                                                }
//
//                                            }
//                                    ) {
//                                        Icon(
//                                            painter = painterResource(
//                                                if (activeScheduleId.value != schedule.id.toString()) R.drawable.ic_compass
//                                            else R.drawable.ic_pause),
//                                            contentDescription = null,
//                                            tint = Color.White,
//                                            modifier = Modifier.size(if (activeScheduleId.value == schedule.id.toString()) 22.dp else 18.dp)
//                                        )
//                                        Spacer(modifier = Modifier.width(2.dp))
//                                        Text(
//                                            text = if (activeScheduleId.value != schedule.id.toString()) "Bắt đầu" else "Dừng lại",
//                                            fontSize = 15.sp,
//                                            color = Color.White,
//                                            fontWeight = FontWeight.W500,
//                                            textAlign = TextAlign.Center
//                                        )
//                                    }
//                                    Spacer(modifier = Modifier.width(5.dp))
//                                    Icon(
//                                        painter = painterResource(R.drawable.ic_vertical_dot),
//                                        contentDescription = null,
//                                        tint = colorResource(id = R.color.background_gray_color) ,
//                                        modifier = Modifier.size(20.dp)
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        ScheduleTab(viewModel ,showBottomSheet = { showBottomSheet(it) }, scrollState = scrollState, schedule)
//    }
//}
//
//@Composable
//fun ScheduleTab(
//    viewModel: ScheduleViewModel,
//    showBottomSheet: (BottomSheetType) -> Unit = {},
//    scrollState: ScrollState,
//    schedule: Schedule
//){
//
//    val tabsName = listOf("Tổng quan", "Chi tiết", "Bản đồ")
//    val tabsIcon = listOf(R.drawable.ic_home_solid, R.drawable.ic_booking_list, R.drawable.ic_map)
//    val tabsSelectedIcon = listOf(
//        R.drawable.ic_home_black_24dp,
//        R.drawable.ic_booking_list_selected,
//        R.drawable.ic_map_pin_selected
//    )
//    val density = LocalDensity.current
//    val offsetPx = with(density) { 66.dp.toPx() }
//    var tabRowY by remember { mutableStateOf(0f) }
//    var tabRowHeight by remember { mutableStateOf(0f) }
//    var selectedTabIndex by remember { mutableStateOf(0) }
//    val activity = LocalActivity.current
//    Column (modifier = Modifier
//        .heightIn(min = 100.dp, max = 10000.dp)
//        .offset(y = (-50).dp)
//        .zIndex(0f)
//    ) {
//        Column(modifier = Modifier
//            .padding(top = 10.dp)
//            .onGloballyPositioned { coordinates ->
//                if (tabRowY == 0f) {
//                    tabRowY = coordinates.positionInWindow().y
//                    tabRowHeight = coordinates.size.height.toFloat()
//                }
//            }
//            .graphicsLayer {
//                translationY =
//                    max(a = 0f, b = scrollState.value - tabRowY + tabRowHeight - offsetPx)
//            }
//            .zIndex(1f)
//            .background(Color.White)
//        ) {
//            Spacer(modifier = Modifier.height(10.dp))
//            Row (horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 10.dp, vertical = 5.dp)
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_home_black_24dp),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(25.dp)
//                        .clickable { activity?.finish() }
//                )
//                Column (modifier = Modifier.weight(1f),
//                    horizontalAlignment = Alignment.CenterHorizontally) {
//                    Text(text = schedule.scheduleName.toString(), fontSize = 15.sp,
//                        color = Color.DarkGray,
//                        fontWeight = FontWeight.W700)
//                }
//                Row (verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_share),
//                        contentDescription = null,
//                        tint = Color.DarkGray,
//                        modifier = Modifier.size(22.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.width(5.dp))
//                Row (verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_vertical_dot),
//                        contentDescription = null,
//                        tint = Color.DarkGray,
//                        modifier = Modifier.size(22.dp)
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(15.dp))
//            TabRow(selectedTabIndex = selectedTabIndex,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.White),
//                indicator = { tabPositions ->
//                    SecondaryIndicator(
//                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
//                        color = colorResource(id = R.color.blue),
//                        height = 2.dp
//                    )
//                }
//            ) {
//                tabsName.forEachIndexed { index, title ->
//                    Tab(
//                        selected = selectedTabIndex == index,
//                        modifier = Modifier
//                            .height(30.dp)
//                            .background(Color.White),
//                        onClick = { selectedTabIndex = index },
//                        text = {
//                            Row (horizontalArrangement = Arrangement.Center,
//                                verticalAlignment = Alignment.CenterVertically) {
//                                Image(
//                                    painter =if (selectedTabIndex == index) {
//                                        painterResource(tabsSelectedIcon[index])
//                                    } else {
//                                        painterResource(tabsIcon[index])
//                                    },
//                                    contentScale = ContentScale.Crop,
//                                    contentDescription = null,
//                                    colorFilter = if (selectedTabIndex == index) {
//                                        ColorFilter.tint(colorResource(id = R.color.blue))
//                                    } else {
//                                        ColorFilter.tint(Color.Gray)
//                                    },
//                                    modifier = Modifier.size(20.dp)
//                                )
//                                Spacer(modifier = Modifier.width(3.dp))
//                                Text(text = title, fontSize = 13.sp,
//                                    color = if (selectedTabIndex == index) colorResource(id = R.color.blue) else Color.DarkGray,
//                                    fontWeight = FontWeight.W700,
//                                    maxLines = 1)
//                            }
//
//                        }
//                    )
//                }
//            }
//        }
//        val scheduleTab = remember { mutableStateOf<@Composable () -> Unit>({ ScheduleSummaryTab(viewModel, showBottomSheet) }) }
//        val summaryTab = remember { mutableStateOf<@Composable () -> Unit>({ ScheduleDetailTab(viewModel, showBottomSheet) }) }
//        val detailTab = remember { mutableStateOf<@Composable () -> Unit>({ ScheduleMapTab() }) }
//
//        AnimatedContent(
//            targetState = selectedTabIndex,
//            transitionSpec = {
//                val direction = if (targetState > initialState) 1 else -1
//                slideInHorizontally { width -> direction * width } togetherWith
//                        slideOutHorizontally { width -> -direction * width }
//            },
//            label = "TabTransition"
//        ) { tabIndex ->
//            when (tabIndex) {
//                0 -> scheduleTab.value.invoke()
//                1 -> summaryTab.value.invoke()
//                2 -> detailTab.value.invoke()
//            }
//        }
//    }
//}
//
///// SummaryTab Section
//@Composable
//fun ScheduleSummaryTab(
//    viewModel: ScheduleViewModel,
//    showBottomSheet: (BottomSheetType) -> Unit = {},
//){
//    val schedule = viewModel.schedule
//    var description by remember { mutableStateOf(schedule?.description ?: "") }
//    var isDescriptionExpanded by rememberSaveable  { mutableStateOf(true) }
//    var iconLike by remember { mutableStateOf(R.drawable.ic_favorite) }
//    val scale = remember { Animatable(1f) }
//    val coroutineScope = rememberCoroutineScope()
//    fun onLikeClick() {
//        iconLike = if (iconLike == R.drawable.ic_favorited) {
//            R.drawable.ic_favorite
//        } else {
//            R.drawable.ic_favorited
//        }
//        coroutineScope.launch {
//            scale.animateTo(
//                targetValue = 1.2f, // Nở ra
//                animationSpec = tween(durationMillis = 150)
//            )
//            scale.animateTo(
//                targetValue = 1f, // Co lại
//                animationSpec = tween(durationMillis = 150)
//            )
//        }
//    }
//    fun countComments(comments: List<Comment>?): Int {
//        if (comments.isNullOrEmpty()) return 0
//        return comments.sumOf { comment ->
//            1 + (comment.replies?.size ?: 0)
//        }
//    }
//    fun countActivitiesByType(type: String): Int {
//        var count = 0
//        schedule?.activities?.forEach { activityDay ->
//                activityDay.activity?.forEach { activity ->
//                    if (activity.activityType == type) {
//                        count++
//                    }
//                }
//            }
//        return count
//    }
//
//    LaunchedEffect(Unit) {
//        snapshotFlow { description }
//            .debounce(3000) // 3 giây
//            .distinctUntilChanged()
//            .collect { newDescription ->
//                val newSchedule = schedule?.copy(description = newDescription)
//                viewModel.updateSchedule(newSchedule)
//            }
//    }
//    Surface(
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        Column {
//            Column (modifier = Modifier
//                .heightIn(min = 100.dp, max = 5000.dp)
//                .background(colorResource(id = R.color.bgRate))) {
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(modifier = Modifier
//                    .background(Color.White)
//                    .fillMaxWidth()
//                    .padding(horizontal = 12.dp, vertical = 12.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(10.dp)
//                ) {
//                    Column (modifier = Modifier.weight(1f),
//                        horizontalAlignment = Alignment.CenterHorizontally){
//                        Image(
//                            painter = painterResource(iconLike),
//                            contentDescription = null,
//                            colorFilter = if (iconLike == R.drawable.ic_favorited) {
//                                ColorFilter.tint(Color.Red)
//                            } else {
//                                ColorFilter.tint(Color.Black)
//                            },
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .size(25.dp)
//                                .graphicsLayer(
//                                    scaleX = scale.value,
//                                    scaleY = scale.value
//                                )
//                                .clickable { onLikeClick() }
//                        )
//                        Spacer(modifier = Modifier.height(3.dp))
//                        Text(text = schedule?.likes?.size.toString(), fontSize = 12.sp,  color = Color.Gray)
//                    }
//                    VerticalDivider(thickness = 0.5.dp, color = Color.LightGray,
//                        modifier = Modifier.height(20.dp))
//                    Column (modifier = Modifier
//                        .weight(1f)
//                        .clip(RoundedCornerShape(8.dp))
//                        .clickable { showBottomSheet(BottomSheetType.COMMENT) },
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_comment_solid),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.size(25.dp)
//                        )
//                        Spacer(modifier = Modifier.height(3.dp))
//                        Text(text = countComments(schedule?.comments).toString(), fontSize = 12.sp,  color = Color.Gray)
//                    }
//                    VerticalDivider(thickness = 0.5.dp, color = Color.LightGray,
//                        modifier = Modifier.height(20.dp))
//                    Column (modifier = Modifier.weight(1f),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_accommodation_selected),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.size(25.dp)
//                        )
//                        Spacer(modifier = Modifier.height(3.dp))
//                        Text(text = countActivitiesByType("Accommodation").toString(), fontSize = 12.sp,  color = Color.Gray)
//                    }
//                    VerticalDivider(thickness = 0.5.dp, color = Color.LightGray,
//                        modifier = Modifier.height(20.dp))
//                    Column (modifier = Modifier.weight(1f),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_food),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.size(25.dp)
//                        )
//                        Spacer(modifier = Modifier.height(3.dp))
//                        Text(text = countActivitiesByType("FoodService").toString(), fontSize = 12.sp,  color = Color.Gray)
//                    }
//                    VerticalDivider(thickness = 0.5.dp, color = Color.LightGray,
//                        modifier = Modifier.height(20.dp))
//                    Column (modifier = Modifier.weight(1f),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_beach),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.size(25.dp)
//                        )
//                        Spacer(modifier = Modifier.height(3.dp))
//                        Text(text = countActivitiesByType("Attraction").toString(), fontSize = 12.sp,  color = Color.Gray)
//                    }
//                    VerticalDivider(thickness = 0.5.dp, color = Color.LightGray,
//                        modifier = Modifier.height(20.dp))
//                    Column (modifier = Modifier.weight(1f),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_notification),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier.size(25.dp)
//                        )
//                        Spacer(modifier = Modifier.height(3.dp))
//                        Text(text = countActivitiesByType("Other").toString(), fontSize = 12.sp,  color = Color.Gray)
//                    }
//                }
//                Spacer(modifier = Modifier.height(18.dp))
//                Column (modifier = Modifier.background(Color.White)) {
//                    Row (modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            painter = if (!isDescriptionExpanded) painterResource(R.drawable.ic_arrow_right)
//                            else painterResource(R.drawable.ic_arrow_down),
//                            contentDescription = null,
//                            tint = Color.DarkGray,
//                            modifier = Modifier
//                                .padding(start = 12.dp)
//                                .size(20.dp)
//                                .clickable { isDescriptionExpanded = !isDescriptionExpanded }
//                        )
//                        TextField(
//                            value = "Mô tả",
//                            onValueChange = {},
//                            enabled = false,
//                            textStyle = TextStyle(
//                                fontWeight = FontWeight.W700,
//                                fontSize = 20.sp,
//                            ),
//                            colors = TextFieldDefaults.colors(
//                                focusedContainerColor = Color.Transparent, // Nền trong suốt khi chọn
//                                unfocusedContainerColor = Color.Transparent, // Nền trong suốt khi không chọn
//                                disabledContainerColor = Color.Transparent, // Nền trong suốt khi bị vô hiệu hóa
//                                focusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi chọn
//                                unfocusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi không chọn
//                                disabledIndicatorColor = Color.Transparent,
//                                disabledTextColor = Color.Black,
//
//                                ),
//                            modifier = Modifier
//                                .weight(1f) // Căn lề theo chiều rộng
//                                .padding(horizontal = 0.dp, vertical = 0.dp) // Giảm padding
//                        )
//                        Icon(
//                            painter = painterResource(R.drawable.ic_vertical_dot),
//                            contentDescription = null,
//                            tint = Color.DarkGray ,
//                            modifier = Modifier
//                                .padding(end = 10.dp)
//                                .size(20.dp)
//                        )
//                    }
//                    AnimatedVisibility(
//                        visible = isDescriptionExpanded,
//                        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
//                        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
//                    ) {
//                        TextField(
//                            value = description.toString(),
//                            onValueChange = { description = it },
//                            textStyle = TextStyle(
//                                fontWeight = FontWeight.W400,
//                                fontSize = 16.sp,
//                                color = Color.DarkGray
//                            ),
//                            colors = TextFieldDefaults.colors(
//                                focusedContainerColor = colorResource(id = R.color.colorSeparator80),
//                                unfocusedContainerColor = Color.Transparent,
//                                disabledContainerColor = Color.Transparent,
//                                focusedIndicatorColor = Color.Transparent,
//                                unfocusedIndicatorColor = Color.Transparent,
//                                disabledIndicatorColor = Color.Transparent,
//                            ),
//                            modifier = Modifier
//                                .offset(y = (-10).dp)
//                                .fillMaxWidth()
//                                .padding(horizontal = 1.dp, vertical = 0.dp)
//                                .clip(RoundedCornerShape(8.dp))
//                                .heightIn(min = 70.dp),
//                            singleLine = false,
//                            minLines = 2,
//                            maxLines = 4
//                        )
//                    }
//                }
//                schedule?.activities?.forEachIndexed { index, item ->
//                    Spacer(modifier = Modifier.height(18.dp))
//                    SummaryDay(viewModel,items = "Ngày ${index + 1}", showBottomSheet = { showBottomSheet(it) }, activities = item)
//                }
//                Spacer(modifier = Modifier.height(18.dp))
//                PriceSection(schedule)
//            }
//        }
//    }
//}
//
//@Composable
//fun SummaryDay(
//    viewModel: ScheduleViewModel,
//    showBottomSheet: (BottomSheetType) -> Unit = {},
//    items: String = "",
//    activities: DayActivity
//){
//    var isExpanded by rememberSaveable { mutableStateOf(true) }
//    Column (modifier = Modifier.background(Color.White)) {
//        Row (modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
//            Icon(
//                painter = if (!isExpanded) painterResource(R.drawable.ic_arrow_right)
//                else painterResource(R.drawable.ic_arrow_down),
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(start = 12.dp)
//                    .size(20.dp)
//                    .clickable { isExpanded = !isExpanded }
//            )
//            TextField(
//                value = items,
//                onValueChange = {},
//                enabled = false,
//                textStyle = TextStyle(
//                    fontWeight = FontWeight.W700,
//                    fontSize = 20.sp,
//                ),
//                colors = TextFieldDefaults.colors(
//                    focusedContainerColor = Color.Transparent,
//                    unfocusedContainerColor = Color.Transparent, // Nền trong suốt khi không chọn
//                    disabledContainerColor = Color.Transparent, // Nền trong suốt khi bị vô hiệu hóa
//                    focusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi chọn
//                    unfocusedIndicatorColor = Color.Transparent, // Ẩn gạch chân khi không chọn
//                    disabledIndicatorColor = Color.Transparent,
//                    disabledTextColor = Color.Black,
//                    ),
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 0.dp, vertical = 0.dp) // Giảm padding
//            )
//            Icon(
//                painter = painterResource(R.drawable.ic_vertical_dot),
//                contentDescription = null,
//                tint = Color.DarkGray ,
//                modifier = Modifier
//                    .padding(end = 10.dp)
//                    .size(20.dp)
//            )
//        }
//        AnimatedVisibility(
//            visible = isExpanded,
//            enter = expandVertically(animationSpec = tween(500)) + fadeIn(),
//            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
//        ) {
//            Column (modifier = Modifier.background(Color.White)) {
//                LazyColumn (modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 3.dp),
//                    verticalArrangement = Arrangement.spacedBy(5.dp)) {
//                    itemsIndexed(activities.activity!!, key = { index, item -> item.id.toString() }) { index, item ->
//                        ActivityCard(viewModel ,showBottomSheet, index, item)
//                    }
//
//                }
//
//                Row (modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Row(verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(8.dp))
//                            .clickableWithScale { showBottomSheet(BottomSheetType.ADD_ACTIVITY) }
//                            .background(colorResource(id = R.color.bgRate))
//                            .weight(1f)
//                            .padding(horizontal = 12.dp, vertical = 10.dp)
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_marked),
//                            contentDescription = null,
//                            tint = Color.DarkGray,
//                            modifier = Modifier.size(20.dp)
//                        )
//                        Spacer(modifier = Modifier.width(6.dp))
//                        Text(
//                            text = "Thêm hoạt động mới",
//                            fontSize = 14.sp,
//                            fontWeight = FontWeight.W400,
//                            color = Color.Gray,
//                        )
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Row (modifier = Modifier
//                        .clip(RoundedCornerShape(99.dp))
//                        .background(colorResource(id = R.color.bgRate))
//                        .padding(horizontal = 10.dp, vertical = 8.dp)
//                        .clickableWithScale { showBottomSheet(BottomSheetType.DRAG_ITEM) }
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_drag),
//                            contentDescription = null,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//                }
//            }
//
//
//        }
//    }
//}
//
//fun calculateAverageRating(ratings: List<Rating>?): Double {
//    if (ratings.isNullOrEmpty()) return 0.0
//    val totalStars = ratings.sumOf { it.rate ?: 0 }
//    return totalStars.toDouble() / ratings.size
//}
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun ActivityCard(
//    viewModel: ScheduleViewModel,
//    showBottomSheet: (BottomSheetType) -> Unit = {},
//    index: Int,
//    item: ActivityItem
//){
//    val type = when (item.activityType) {
//        "Attraction" -> "Tham quan"
//        "Accommodation" -> "Nghỉ ngơi"
//        "FoodService" -> "Ẩm thực"
//        else -> "Khác"
//    }
//    val schedule = viewModel.schedule
//    val attraction = item.destination as? Attraction
//    val accommodation = item.destination as? Accommodation
//    val foodService = item.destination as? FoodService
//
//    val operatingHours = attraction?.operatingHours?.firstOrNull() ?: foodService?.operatingHours?.firstOrNull()
//    val price = foodService?.price?.minPrice ?: attraction?.price ?: 0
//    val ratings = attraction?.ratings ?: foodService?.ratings ?: accommodation?.ratings
//
//    val imageSrc = accommodation?.images?.firstOrNull()
//        ?: attraction?.images?.firstOrNull()
//        ?: foodService?.images?.firstOrNull()
//        ?: item.imgSrc?.firstOrNull()
//
//    var seeMoreExtension by rememberSaveable { mutableStateOf(false) }
//    var isShowDetail by rememberSaveable { mutableStateOf(false) }
//    val keyboardController = LocalSoftwareKeyboardController.current
//    var userNote by rememberSaveable { mutableStateOf(item.description) }
//    val serviceList = (accommodation?.amenities ?: attraction?.amenities ?: foodService?.amenities ?: listOf())
//    var showDialog by remember { mutableStateOf(false) }
//    val context = LocalContext.current
//    LaunchedEffect(Unit) {
//        snapshotFlow { userNote }
//            .debounce(3000)
//            .distinctUntilChanged()
//            .collect { newUserNote ->
//                val newSchedule = schedule?.copy(
//                    activities = schedule.activities?.map { day ->
//                        day.copy(
//                            activity = day.activity?.map { activity ->
//                                if (activity.id == item.id) {
//                                    activity.copy(description = newUserNote)
//                                } else activity
//                            }
//                        )
//                    }
//                )
//                if (newSchedule != null) {
//                    viewModel.updateSchedule(newSchedule)
//                }
//            }
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 5.dp)
//            .background(
//                color = if (!isShowDetail) Color.White else colorResource(id = R.color.colorSeparator),
//                shape = RoundedCornerShape(8.dp)
//            )
//            .padding(10.dp)
//            .clickableWithScale { isShowDetail = !isShowDetail }
//    ) {
//        Row (horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Box(
//                    modifier = Modifier
//                        .size(28.dp),
//                    contentAlignment = Alignment.Center,
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_marked),
//                        contentDescription = null,
//                        tint = colorResource(id = R.color.purple_700),
//                        modifier = Modifier.fillMaxSize()
//                    )
//                    Text(
//                        text = (index + 1).toString(),
//                        fontSize = 11.sp,
//                        fontWeight = FontWeight.W600,
//                        color = Color.White,
//                        modifier = Modifier.offset(x = (-2).dp, y = (-2).dp))
//                }
//                Spacer(modifier = Modifier.width(6.dp))
//                Text(
//                    text = type,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.W700,
//                    textAlign = TextAlign.Center
//                )
//            }
//            Row(verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .clickable {
//                        viewModel.activityId = item.id.toString()
//                        showBottomSheet(BottomSheetType.TIME_PICKER)
//                    }
//                    .background(
//                        colorResource(id = R.color.timeScheduleBackground),
//                        shape = RoundedCornerShape(8.dp)
//                    )
//                    .padding(horizontal = 5.dp, vertical = 3.dp)
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_watch),
//                    contentDescription = null,
//                    tint = colorResource(id = R.color.lightDarkBlue),
//                    modifier = Modifier.size(22.dp)
//                )
//                Spacer(modifier = Modifier.width(6.dp))
//                Text(
//                    text = item.timeStart + " - " + item.timeEnd,
//                    fontSize = 15.sp,
//                    color = colorResource(id = R.color.lightDarkBlue),
//                    fontWeight = FontWeight.W600,
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(3.dp))
//        Row {
//            Column (modifier = Modifier.weight(1f)) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_aim),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(15.dp)
//                            .align(Alignment.Top)
//                    )
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(
//                        text = (accommodation?.name ?: attraction?.attractionName ?: foodService?.foodServiceName ?: item.name).toString(),
//                        fontSize = 15.sp,
//                        fontWeight = FontWeight.W600,
//                        maxLines = 4,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//                Spacer(modifier = Modifier.height(3.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    if (!isShowDetail){
//                        Icon(
//                            painter = painterResource(R.drawable.ic_saved_list),
//                            contentDescription = null,
//                            tint = Color.DarkGray,
//                            modifier = Modifier
//                                .size(15.dp)
//                                .align(Alignment.Top)
//                        )
//                        Spacer(modifier = Modifier.width(6.dp))
//                    }
//                    BasicTextField(
//                        value = userNote.toString(),
//                        onValueChange = { userNote = it },
//                        textStyle = TextStyle(
//                            fontSize = 13.sp,
//                            fontWeight = FontWeight.W400,
//                            color = Color.DarkGray
//                        ),
//                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//                        keyboardActions = KeyboardActions(
//                            onDone = {
//                                keyboardController?.hide()
//                            }
//                        ),
//                        enabled = isShowDetail,
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        maxLines = if (!isShowDetail) 4 else 15,
//                        decorationBox = { innerTextField ->
//                            innerTextField()
//                        }
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.width(5.dp))
//            //////
//
//
//            //////
//            Image(
//                painter = rememberAsyncImagePainter(
//                    model = imageSrc,
//                    error = painterResource(R.drawable.img_ha_long)
//                ),
//                contentScale = ContentScale.Crop,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(80.dp)
//                    .clip(RoundedCornerShape(8.dp))
//            )
//        }
//        Spacer(modifier = Modifier.height(8.dp))
//        Row(verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.End,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row (modifier = Modifier
//                .background(
//                    colorResource(id = R.color.priceScheduleBackground),
//                    shape = RoundedCornerShape(8.dp)
//                )
//                .padding(horizontal = 5.dp, vertical = 3.dp)
//                .clickable(
//                    indication = null,
//                    interactionSource = null
//                ) {
//                    viewModel.activityId = item.id.toString()
//                    showBottomSheet(BottomSheetType.PRICE_PICKER) },
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.ic_cost),
//                    contentDescription = null,
//                    tint = Color.Red,
//                    modifier = Modifier.size(15.dp)
//                )
//                Spacer(modifier = Modifier.width(6.dp))
//                Text(
//                    text = formatCurrency(item.cost.toString()) + " đ",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.W500,
//                    color = Color.Red,
//                    maxLines = 4,
//                    overflow = TextOverflow.Ellipsis,
//                )
//            }
//        }
//    }
//    Spacer(modifier = Modifier.height(5.dp))
//    AnimatedVisibility(
//        visible = isShowDetail,
//        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
//        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
//    ) {
//        Column (modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 10.dp)) {
//            if (serviceList.isNotEmpty()) {
//                Box(contentAlignment = Alignment.CenterStart,
//                    modifier = Modifier.fillMaxWidth()){
//                    FlowRow(
//                        horizontalArrangement = Arrangement.spacedBy(6.dp),
//                        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
//                        overflow = FlowRowOverflow.Clip,
//                    ) {
//                        serviceList.take(if (seeMoreExtension) 10 else 3).forEach { item ->
//                            Row(
//                                modifier = Modifier
//                                    .background(
//                                        colorResource(id = R.color.colorSeparator80),
//                                        shape = RoundedCornerShape(6.dp)
//                                    )
//                                    .padding(horizontal = 10.dp, vertical = 5.dp),
//                            ) {
//                                Text(
//                                    text = item,
//                                    fontWeight = FontWeight.W600,
//                                    fontSize = 13.sp,
//                                    color = Color.DarkGray,
//                                    textAlign = TextAlign.Center
//                                )
//                            }
//                        }
//                    }
//                    if (!seeMoreExtension){
//                        Row(verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                .clickable { seeMoreExtension = true }
//                                .align(Alignment.CenterEnd)
//                                .background(
//                                    Color.White.copy(alpha = 0.8f),
//                                    shape = RoundedCornerShape(6.dp)
//                                )
//                                .padding(vertical = 6.dp, horizontal = 8.dp)
//                        ) {
//                            Text(
//                                text = "Xem thêm",
//                                fontSize = 11.sp,
//                                fontWeight = FontWeight.W600,
//                                color = Color.DarkGray,
//                                overflow = TextOverflow.Ellipsis
//                            )
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//            }
//            Column(modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 4.dp)) {
//                if (item.activityType != "Other"){
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_star),
//                            contentDescription = null,
//                            tint = colorResource(id = R.color.first_ranking),
//                            modifier = Modifier.size(15.dp)
//                        )
//                        Spacer(modifier = Modifier.width(10.dp))
//                        Text(
//                            text = calculateAverageRating(ratings).toString() + "★ (" + ratings?.size.toString() + ")",
//                            fontSize = 13.sp,
//                            fontWeight = FontWeight.W400,
//                            color = colorResource(id = R.color.first_ranking),
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_information),
//                        contentDescription = null,
//                        tint = colorResource(id = R.color.highlight_route),
//                        modifier = Modifier
//                            .size(15.dp)
//                            .align(Alignment.Top)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = accommodation?.description ?: attraction?.description ?: foodService?.description ?: item.description.toString(),
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.W400,
//                        color = Color.DarkGray,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_location),
//                        contentDescription = null,
//                        tint = Color.DarkGray,
//                        modifier = Modifier
//                            .size(15.dp)
//                            .align(Alignment.Top)
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = accommodation?.location?.address ?: attraction?.location?.address ?: foodService?.location?.address ?: item.address.toString(),
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.W400,
//                        color = colorResource(id = R.color.darker_blue),
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//                if (operatingHours != null) {
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_watch),
//                            contentDescription = null,
//                            tint = Color.DarkGray,
//                            modifier = Modifier
//                                .size(15.dp)
//                                .align(Alignment.Top)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = operatingHours?.openTime + " - " + operatingHours?.closeTime + " (" + operatingHours?.startDay + " - " + operatingHours?.endDay + ")",
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.W600,
//                            color = Color.DarkGray,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(6.dp))
//
//                if (item.activityType == "FoodService" || item.activityType == "Attraction"){
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_dollar_bag),
//                            contentDescription = null,
//                            tint = Color.DarkGray,
//                            modifier = Modifier
//                                .size(15.dp)
//                                .align(Alignment.Top)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = formatCurrency(price.toString()) + " đ",
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.W600,
//                            color = Color.DarkGray,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    }
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Row (modifier = Modifier
//                        .background(
//                            colorResource(id = R.color.darkOrange),
//                            shape = RoundedCornerShape(99.dp)
//                        )
//                        .padding(horizontal = 10.dp, vertical = 8.dp)
//                        .clickableWithScale{
//                            // Thay đổi toạ độ và tên điểm đến tại đây
//                            var uri = "".toUri()
//                            val lat = accommodation?.location?.latitude ?: attraction?.location?.latitude ?: foodService?.location?.latitude
//                            val lng = accommodation?.location?.longitude ?: attraction?.location?.longitude ?: foodService?.location?.longitude
//                            val label = accommodation?.location?.address ?: attraction?.location?.address ?: foodService?.location?.address
//
//                            uri = if (lat == null || lng == null) {
//                                val address = item.address.toString()
//                                if (address == ""){
//                                    return@clickableWithScale
//                                } else
//                                    "geo:0,0?q=$address".toUri()
//                            }else {
//                                "geo:$lat,$lng?q=$lat,$lng($label)".toUri()
//                            }
//                            // Chỉ đường
//                            // val uri = Uri.parse("google.navigation:q=$lat,$lng")
//
//                            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
//                                setPackage("com.google.android.apps.maps")
//                            }
//
//                            if (intent.resolveActivity(context.packageManager) != null) {
//                                context.startActivity(intent)
//                        }
//                        },
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "Đường đi",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.W600,
//                            color = Color.White,
//                        )
//                    }
//                    Spacer(modifier = Modifier.weight(1f))
//                    Icon(painter = painterResource(R.drawable.ic_garbage_bin),
//                        contentDescription = null,
//                        tint = Color.DarkGray,
//                        modifier = Modifier.size(20.dp)
//                            .clickableWithScale{showDialog = true})
//                    Spacer(modifier = Modifier.width(16.dp))
//                    Icon(painter = painterResource(R.drawable.ic_arrow_up),
//                        contentDescription = null,
//                        tint = Color.DarkGray,
//                        modifier = Modifier.size(20.dp))
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//            }
//        }
//    }
//    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
//}
//
//@Composable
//fun PriceSection(
//    schedule: Schedule?
//){
//    val total = schedule?.calculateTotalCost()
//    var isExpanded by remember { mutableStateOf(false) }
//    Column(modifier = Modifier.background(Color.White)){
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(colorResource(id = R.color.dark_slate_blue))
//                .padding(top = 20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Text(text = formatCurrency(total.toString()) , fontSize = 28.sp,
//                color = Color.White,
//                fontWeight = FontWeight.W700,
//                letterSpacing = 1.5.sp)
//            Spacer(modifier = Modifier.height(15.dp))
//            Row(verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .clip(RoundedCornerShape(99.dp))
//                    .clickable { isExpanded = !isExpanded }
//                    .border(
//                        width = 2.dp,
//                        color = Color.White,
//                        shape = RoundedCornerShape(99.dp)
//                    )
//                    .padding(horizontal = 20.dp, vertical = 8.dp)
//
//            ) {
//                Text(text = "Xem chi tiết" , fontSize = 20.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.W500,
//                    maxLines = 1)
//                Icon(
//                    painter = if (!isExpanded) painterResource(R.drawable.ic_arrow_right)
//                    else painterResource(R.drawable.ic_arrow_down),
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier
//                        .padding(start = 6.dp)
//                        .size(18.dp)
//                )
//            }
//            Spacer(modifier = Modifier.height(30.dp))
//        }
//        AnimatedVisibility(
//            visible = isExpanded,
//            enter = expandVertically(animationSpec = tween(500)) + fadeIn(),
//            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
//        ) {
//            Column(modifier = Modifier
//                .fillMaxWidth()
//                .offset(y = (-12).dp)
//                .clip(RoundedCornerShape(16.dp))
//                .background(Color.White)
//                .padding(vertical = 10.dp, horizontal = 15.dp)
//            ) {
//                Row (verticalAlignment = Alignment.CenterVertically) {
//                    Text(text = "Chi tiêu" , fontSize = 25.sp, fontWeight = FontWeight.W700)
//                    Spacer(modifier = Modifier.weight(1f))
//                    Text(
//                        text = "Sắp xếp: ",
//                        fontWeight = FontWeight.W600,
//                        fontSize = 15.sp,
//                        textAlign = TextAlign.Center,
//                    )
//                    Text(
//                        text = "Ngày (tăng dần)",
//                        fontWeight = FontWeight.W400,
//                        color = Color.Gray,
//                        fontSize = 15.sp,
//                        textAlign = TextAlign.Center,
//                    )
//                    Spacer(modifier = Modifier.width(3.dp))
//                    Icon(
//                        painter = painterResource(R.drawable.ic_sort),
//                        contentDescription = null,
//                        modifier = Modifier.size(15.dp)
//                    )
//                }
//                Spacer(modifier = Modifier.height(15.dp))
//                schedule?.activities?.forEach { day ->
//                    day.activity?.filter { (it.cost ?: 0) > 0 }?.forEach { activity ->
//                        PriceItem(
//                            title = activity.costDescription ?: activity.name.toString(),
//                            price = activity.cost ?: 0,
//                            type = activity.activityType
//                        )
//                    }
//                }
//                schedule?.additionalExpenses?.filter { (it.cost ?: 0) > 0 }?.forEach { expense ->
//                    PriceItem(
//                        title = expense.description ?: expense.description ?: "Chi phí khác",
//                        price = expense.cost ?: 0,
//                        type = "other"
//                    )
//                }
//                //// Thiếu thêm chi phí phát sinh
//                Spacer(modifier = Modifier.height(30.dp))
//            }
//
//        }
//    }
//}
//
//@Composable
//fun PriceItem(
//    title: String,
//    price: Int,
//    type: String
//){
//    val typeName = if (type == "Accommodation") "Chỗ nghỉ"
//    else if (type == "FoodService") "Ăn uống"
//    else if (type == "Attraction") "Tham quan"
//    else "Hoạt động khác"
//    Row (verticalAlignment = Alignment.CenterVertically) {
//        Row (modifier = Modifier
//            .clip(RoundedCornerShape(99.dp))
//            .background(colorResource(id = R.color.colorSeparator80))
//            .padding(10.dp)
//        ) {
//            Icon(
//                painter = painterResource(if (type == "Accommodation") R.drawable.ic_accommodation_selected
//                else if (type == "FoodService") R.drawable.ic_food
//                else if (type == "Attraction") R.drawable.ic_beach
//                else R.drawable.ic_notification),
//                contentDescription = null,
//                modifier = Modifier.size(22.dp)
//            )
//        }
//        Spacer(modifier = Modifier.width(10.dp))
//        Column {
//            Text(
//                text = title,
//                fontWeight = FontWeight.W600,
//                fontSize = 15.sp,
//                textAlign = TextAlign.Center,
//            )
//            Spacer(modifier = Modifier.height(3.dp))
//            Text(
//                text = typeName,
//                fontWeight = FontWeight.W400,
//                color = Color.Gray,
//                fontSize = 14.sp,
//                textAlign = TextAlign.Center,
//            )
//        }
//        Spacer(modifier = Modifier.weight(1f))
//        Text(
//            text = formatCurrency(price.toString()) + " đ",
//            fontWeight = FontWeight.W600,
//            fontSize = 15.sp,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp)
//        )
//    }
//    Spacer(modifier = Modifier.height(5.dp))
//    HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
//    Spacer(modifier = Modifier.height(5.dp))
//}
//
//fun Schedule.calculateTotalCost(): Int {
//    val activityCost = activities?.sumOf { day ->
//        day.activity?.sumOf { it.cost ?: 0 } ?: 0
//    } ?: 0
//    val additionalCost = additionalExpenses?.sumOf { it.cost ?: 0 } ?: 0
//    return activityCost + additionalCost
//}
///// DetailTabSection
//
//@Composable
//fun ScheduleDetailTab(
//    viewModel: ScheduleViewModel,
//    showBottomSheet: (BottomSheetType) -> Unit
//){
//    val listState = rememberLazyListState()
//    val coroutineScope = rememberCoroutineScope()
//    val schedule = viewModel.schedule
//    val selectedItem = remember { mutableStateOf(0) }
//    val dayIndexToColumnIndex = remember(schedule) {
//        val map = mutableMapOf<Int, Int>()
//        var currentIndex = 0
//        schedule?.activities?.forEachIndexed { index, dayActivity ->
//            map[dayActivity.day ?: (index + 1)] = currentIndex
//            currentIndex++ // dòng tiêu đề "Ngày X"
//            currentIndex += dayActivity.activity?.size ?: 0 // số hoạt động trong ngày đó
//        }
//        map
//    }
//
//    LaunchedEffect(selectedItem.value) {
//        val targetIndex = dayIndexToColumnIndex[selectedItem.value] ?: 0
//        listState.animateScrollBy(50f)
//        Log.d("ScrollDebug", "Scroll to item index: $targetIndex")
//    }
//    Column {
//        Spacer(modifier = Modifier.height(10.dp))
//        LazyRow (modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 3.dp),
//            horizontalArrangement = Arrangement.spacedBy(10.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            item {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier
//                        .padding(start = 10.dp, end = 30.dp)
//                        .clip(RoundedCornerShape(99.dp))
//                        .background(colorResource(id = R.color.black))
//                        .padding(7.dp)
//                        .clickableWithScale{showBottomSheet(BottomSheetType.RANGE_DAY_PICKER)}
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_calendar),
//                        contentDescription = null,
//                        tint = Color.White ,
//                        modifier = Modifier.size(25.dp)
//                    )
//                }
//            }
//            itemsIndexed(schedule?.activities!!, key = { index, item -> item.day.toString() }) { index, item ->
//                Row (horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxWidth()) {
//                    Row(verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center,
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(8.dp))
//                            .background(colorResource(id = if (selectedItem.value != index) R.color.background
//                            else R.color.colorSeparator))
//                            .padding(vertical = 12.dp, horizontal = 20.dp)
//                            .clickableWithScale {
//                                selectedItem.value = index
//                            }
//                    ) {
//                        Text(
//                            text = "Ngày " + item.day.toString(),
//                            fontSize = 15.sp,
//                            fontWeight = FontWeight.W700,
//                            letterSpacing = 1.5.sp,
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
//            }
//        }
//        Spacer(modifier = Modifier.height(3.dp))
//        LazyColumn (modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 3.dp),
//            state = listState
//        ) {
//            itemsIndexed(schedule?.activities!!, key = { index, item -> item.day.toString() }) { index, item ->
//                Text(
//                    text ="Ngày ${item.day}: ${item.activity?.size} hoạt động",
//                    fontSize = 26.sp,
//                    fontWeight = FontWeight.W700,
//                    letterSpacing = 1.sp,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 5.dp)
//                )
//                item.activity?.forEachIndexed { index, item ->
//                    ActivityCard(viewModel ,showBottomSheet, index, item)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ScheduleMapTab(
//) {
//    val routePoints = listOf(
//        LatLng(10.3401, 107.0843), // Bãi Sau
//        LatLng(10.3372, 107.0840), // Tượng Chúa Kitô Vua
//        LatLng(10.3389, 107.0806), // Hải đăng Vũng Tàu
//        LatLng(10.3460, 107.0848), // Bạch Dinh
//        LatLng(10.3335, 107.0910), // Mũi Nghinh Phong
//    )
//
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(routePoints.first(), 14f)
//    }
//
//    Column {
//        Text(
//            text = "Ngày 1: 15/4/2025",
//            fontSize = 26.sp,
//            fontWeight = FontWeight.W700,
//            letterSpacing = 1.sp,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(start = 10.dp, top = 15.dp, bottom = 5.dp)
//        )
//
//        GoogleMap(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp),
//            cameraPositionState = cameraPositionState
//        ) {
//            // Marker cho từng điểm
//            routePoints.forEachIndexed { index, latLng ->
//                Marker(
//                    state = MarkerState(position = latLng),
//                    title = "Điểm ${index + 1}",
//                    snippet = when (index) {
//                        0 -> "Bãi Sau"
//                        1 -> "Tượng Chúa Kitô Vua"
//                        2 -> "Hải đăng Vũng Tàu"
//                        3 -> "Bạch Dinh"
//                        4 -> "Mũi Nghinh Phong"
//                        else -> "Điểm ${index + 1}"
//                    }
//                )
//            }
//
//            Polyline(
//                points = routePoints,
//                color = Color.Blue,
//                width = 5f
//            )
//        }
//    }
//}
//
