package com.example.vcompass.screen.schedule.schedule_detail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.enum.tab.ScheduleTab
import com.example.vcompass.resource.CoreTypography
import com.example.vcompass.resource.CoreTypographyBold
import com.example.vcompass.resource.CoreTypographySemiBold
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.components.bottom_sheet.AddActivityCostSheet
import com.example.vcompass.ui.components.bottom_sheet.AddActivityTimeSheet
import com.example.vcompass.ui.components.bottom_sheet.AddScheduleActivityPopup
import com.example.vcompass.ui.components.bottom_sheet.DragActivityBottomSheet
import com.example.vcompass.ui.components.bottom_sheet.SelectScheduleDaySheet
import com.example.vcompass.ui.core.animation.StickyHeaderAnimationLayout
import com.example.vcompass.ui.core.general.BaseViewWithPullToRefresh
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.ui.core.icon.MoreOptionIcon
import com.example.vcompass.ui.core.space.ExpandableSpacer
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.core.space.SpaceWidth
import com.example.vcompass.ui.core.space.SpaceWidth4
import com.example.vcompass.ui.core.tab.TabView
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.ui.core.text_field.NormalTextField
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.back
import com.example.vcompass.util.formatDateToDayMonth
import com.vcompass.presentation.enums.BottomSheetType
import com.vcompass.presentation.model.schedule.Schedule
import com.vcompass.presentation.viewmodel.schedule.ScheduleViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersHolder

@Composable
fun ScheduleScreen(params: ParametersHolder) {
    val viewModel: ScheduleViewModel = koinViewModel(parameters = { params })
    val navController = ScreenContext.navController
    val state by viewModel.stateUI.collectAsState()
    val schedule by viewModel.schedule.collectAsState()
    val sheetType by viewModel.sheetType.collectAsState()
    val sheetState = rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(sheetType) {
        sheetState.value = sheetType != BottomSheetType.NONE
    }
    BaseViewWithPullToRefresh(
        viewModel = viewModel,
        onRefresh = { viewModel.getScheduleDetail() },
        state = state,
        navController = navController,
        statusBarPadding = false
    ) {
        StickyHeaderAnimationLayout(
            coverUrl = schedule.images?.firstOrNull(),
            headerSection = { ScheduleHeaderSection(navController, schedule) },
            infoSection = { autoProgress, avatarOffsetX, avatarOffsetY ->
                ScheduleInformationSection(
                    autoProgress,
                    avatarOffsetY,
                    schedule,
                    viewModel
                )
            },
            contentSection = { autoProgress, containerSpace, nestedScrollConnection, contentScrollState ->
                val tabOffsetY = lerp(
                    containerSpace * (1 - autoProgress) + MyDimen.p8,
                    MyDimen.zero,
                    autoProgress
                )
                ScheduleContentSection(
                    nestedScrollConnection = nestedScrollConnection,
                    contentScrollState = contentScrollState,
                    autoProgress = autoProgress,
                    navController = navController,
                    tabOffsetY = tabOffsetY,
                    schedule = schedule
                )
            }
        )
    }
    when (sheetType) {
        BottomSheetType.ADD_ACTIVITY -> AddScheduleActivityPopup(
            sheetState = sheetState,
            onDismiss = { viewModel.hideSheet() }
        ) { activity -> viewModel.addActivity(activity) }

        BottomSheetType.ADD_COST -> AddActivityCostSheet(
            sheetState = sheetState,
            onDismiss = { viewModel.hideSheet() }
        )

        BottomSheetType.DRAG_ACTIVITY -> DragActivityBottomSheet(
            sheetState = sheetState,
            onDismiss = { viewModel.hideSheet() }
        )

        BottomSheetType.ADD_TIME -> AddActivityTimeSheet(
            sheetState = sheetState,
            onDismiss = { viewModel.hideSheet() }
        )

        BottomSheetType.DAY_PICKER -> SelectScheduleDaySheet(
            sheetState = sheetState,
            onDismiss = { viewModel.hideSheet() }
        )

        else -> {}
    }
}

@Composable
fun ScheduleHeaderSection(
    navController: NavController,
    schedule: Schedule
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .zIndex(1f)
            .padding(horizontal = MyDimen.p16, vertical = MyDimen.p6)
    ) {
        CoreIcon(
            resDrawable = R.drawable.ic_home_fill,
            iconModifier = Modifier.size(MyDimen.p20),
            onClick = { navController.back() }
        )
        ExpandableSpacer()
        ScheduleAuthorSection(schedule)
    }
}

@Composable
fun ScheduleInformationSection(
    autoProgress: Float,
    avatarOffsetY: Dp,
    schedule: Schedule,
    viewModel: ScheduleViewModel
) {
    var activeScheduleId by remember { mutableStateOf(schedule.id.toString()) }
    var name by remember { mutableStateOf(schedule.name.toString()) }
    LaunchedEffect(schedule) {
        name = schedule.name.toString()
    }
    if (autoProgress != 1f) {
        Surface(
            shape = RoundedCornerShape(MyDimen.p8),
            shadowElevation = MyDimen.p8,
            color = MyColor.White,
            modifier = Modifier
                .padding(MyDimen.p12)
                .fillMaxWidth()
                .offset(y = avatarOffsetY)
                .alpha(1 - autoProgress * 2)
                .zIndex(2f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MyDimen.p16)
            ) {
                NormalTextField(
                    value = name,
                    onValueChange = { name = it },
                    containerColor = MyColor.White,
                    textStyle = CoreTypographyBold.bodyMedium,
                    label = "",
                    minLines = 2,
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth(),
                    isSingleLine = false
                )
                Row(
                    modifier = Modifier.padding(horizontal = MyDimen.p16),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(MyDimen.p100))
                            .clickable {
                                viewModel.setSheetType(BottomSheetType.DAY_PICKER)
                            }
                    ) {
                        CoreIcon(
                            resDrawable = R.drawable.ic_calendar,
                            iconModifier = Modifier.size(20.dp)
                        )
                        SpaceWidth4()
                        CoreText(
                            text = schedule.dateStart?.formatDateToDayMonth() + " - " + schedule.dateEnd?.formatDateToDayMonth(),
                            style = CoreTypographySemiBold.labelSmall
                        )
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
                    ) {
                        CoreIcon(
                            resDrawable =
                                if (activeScheduleId != schedule.id.toString()) R.drawable.ic_explore_filled_24dp
                                else R.drawable.ic_pause_circle_24dp,
                            tintColor = MyColor.White,
                        )
                        SpaceWidth4()
                        CoreText(
                            text = if (activeScheduleId != schedule.id.toString()) "Bắt đầu" else "Dừng lại",
                            style = CoreTypography.displayMedium,
                            color = MyColor.White,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleAuthorSection(
    schedule: Schedule
) {
    val currentUserAvatar = schedule.user?.avatar ?: ""
    val avatars = listOf(
        currentUserAvatar,
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
                    .offset(x = (15 * (avatars.size - index - 1)).dp)
                    .size(MyDimen.p36)
                    .zIndex((avatars.size - index).toFloat())
                    .background(MyColor.White, CircleShape)
                    .border(MyDimen.p1, MyColor.GrayEEE, CircleShape)
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

@Composable
fun ScheduleContentSection(
    nestedScrollConnection: NestedScrollConnection,
    contentScrollState: ScrollState,
    autoProgress: Float,
    tabOffsetY: Dp,
    navController: NavController,
    schedule: Schedule = Schedule()
) {
    val tabs = ScheduleTab.entries
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = tabOffsetY)
            .zIndex(1f)
            .clip(RoundedCornerShape(topStart = MyDimen.p30, topEnd = MyDimen.p30))
            .background(MyColor.White)
            .statusBarsPadding()
            .nestedScroll(nestedScrollConnection)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(autoProgress * 2)
                .padding(start = MyDimen.p16)
        ) {
            CoreIcon(
                resDrawable = R.drawable.ic_home_fill,
                iconModifier = Modifier.size(MyDimen.p20),
                onClick = { navController.back() }
            )
            SpaceWidth()
            CoreText(
                text = schedule.name.toString(),
                textAlign = TextAlign.Center,
                style = CoreTypographyBold.displayMedium,
                modifier = Modifier.weight(1f)
            )
            SpaceWidth()
            MoreOptionIcon()
        }
        SpaceHeight()
        TabView(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index -> selectedTabIndex = index },
            isScrollable = false,
            tabTitle = { tab -> stringResource(tab.titleRes) },
        ) { tab ->
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(contentScrollState)
            ) {
                when (tab) {
                    ScheduleTab.SUMMARY -> ScheduleSummaryTab(schedule)

                    ScheduleTab.DETAIL -> ScheduleDetailTab(schedule)

                    ScheduleTab.MAP -> ScheduleMapTab()
                }
            }
        }
    }
}




