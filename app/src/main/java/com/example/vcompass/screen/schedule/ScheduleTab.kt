package com.example.vcompass.screen.schedule

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.accessed.core.compose_view.text.CoreText
import com.example.vcompass.R
import com.example.vcompass.data.api.model.Schedule
import com.example.vcompass.helper.BottomSheetType
import com.example.vcompass.ui.core.icon.MoreOptionIcon
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.compose_view.space.SpaceHeight
import com.vcompass.core.compose_view.space.SpaceWidth4
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypographyBold
import kotlin.math.max


@Composable
fun ScheduleTab(
    showBottomSheet: (BottomSheetType) -> Unit = {},
    scrollState: ScrollState,
    schedule: Schedule?
) {
    val tabsName = listOf("Tổng quan", "Chi tiết", "Bản đồ")
    val tabsIcon = listOf(
        R.drawable.ic_home_fill_24dp,
        R.drawable.ic_article_outline_24dp,
        R.drawable.ic_map_outline_24dp
    )
    val tabsSelectedIcon = listOf(
        R.drawable.ic_home_outline_24dp,
        R.drawable.ic_article_fill_24dp,
        R.drawable.ic_map_fill_24dp
    )
    val density = LocalDensity.current
    val offsetPx = with(density) { 66.dp.toPx() }
    var tabRowY by remember { mutableStateOf(0f) }
    var tabRowHeight by remember { mutableStateOf(0f) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    val activity = LocalActivity.current
    Column(
        modifier = Modifier
            .heightIn(min = 100.dp, max = 10000.dp)
            .offset(y = (-50).dp)
            .zIndex(0f)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .onGloballyPositioned { coordinates ->
                    if (tabRowY == 0f) {
                        tabRowY = coordinates.positionInWindow().y
                        tabRowHeight = coordinates.size.height.toFloat()
                    }
                }
                .graphicsLayer {
                    translationY =
                        max(a = 0f, b = scrollState.value - tabRowY + tabRowHeight - offsetPx)
                }
                .zIndex(1f)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                CoreIcon(
                    resDrawable = R.drawable.ic_home_fill_24dp,
                    iconModifier = Modifier.size(MyDimen.p24),
                    onClick = { activity?.finish() }
                )
                CoreText(
                    text = schedule?.scheduleName.toString(),
                    style = CoreTypographyBold.displayMedium
                )
                CoreIcon(
                    resDrawable = R.drawable.ic_share,
                    iconModifier = Modifier.size(22.dp)
                )
                SpaceWidth4()
                MoreOptionIcon()
            }
            SpaceHeight()
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = MyColor.Primary,
                        height = 2.dp
                    )
                }
            ) {
                tabsName.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        modifier = Modifier
                            .height(30.dp)
                            .background(Color.White),
                        onClick = { selectedTabIndex = index },
                        text = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = if (selectedTabIndex == index) {
                                        painterResource(tabsSelectedIcon[index])
                                    } else {
                                        painterResource(tabsIcon[index])
                                    },
                                    contentScale = ContentScale.Crop,
                                    contentDescription = null,
                                    colorFilter = if (selectedTabIndex == index) {
                                        ColorFilter.tint(MyColor.Primary)
                                    } else {
                                        ColorFilter.tint(Color.Gray)
                                    },
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = title, fontSize = 13.sp,
                                    color = if (selectedTabIndex == index) MyColor.Primary else Color.DarkGray,
                                    fontWeight = FontWeight.W700,
                                    maxLines = 1
                                )
                            }

                        }
                    )
                }
            }
        }
        val scheduleTab = remember {
            mutableStateOf<@Composable () -> Unit>({
                ScheduleSummaryTab(
                    showBottomSheet,
                    schedule
                )
            })
        }
        val summaryTab = remember {
            mutableStateOf<@Composable () -> Unit>({
                ScheduleDetailTab(
                    showBottomSheet,
                    schedule
                )
            })
        }
        val detailTab = remember { mutableStateOf<@Composable () -> Unit>({ ScheduleMapTab() }) }

        AnimatedContent(
            targetState = selectedTabIndex,
            transitionSpec = {
                val direction = if (targetState > initialState) 1 else -1
                slideInHorizontally { width -> direction * width } togetherWith
                        slideOutHorizontally { width -> -direction * width }
            },
            label = "TabTransition"
        ) { tabIndex ->
            when (tabIndex) {
                0 -> scheduleTab.value.invoke()
                1 -> summaryTab.value.invoke()
                2 -> detailTab.value.invoke()
            }
        }
    }
}