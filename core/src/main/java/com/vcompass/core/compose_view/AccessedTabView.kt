package com.vcompass.core.compose_view

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.vcompass.core.R
import com.vcompass.core.resource.MyColor
import com.vcompass.core.typography.CoreTypography
import kotlinx.coroutines.launch

@Composable
fun <T> AccessedTabView(
    modifier: Modifier = Modifier,
    tabs: List<T>,
    selectedTabIndex: Int = 0, // use to scroll to that tab
    isScrollable: Boolean = false,
    tabTitle: @Composable (T) -> String,
    content: @Composable (T) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { tabs.size})
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex in tabs.indices && selectedTabIndex != pagerState.currentPage) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        val currentPage = pagerState.currentPage
        val indicator: @Composable (List<TabPosition>) -> Unit = { tabPositions ->
            val currentPage = pagerState.currentPage
            val offset = pagerState.currentPageOffsetFraction.coerceIn(-1f, 1f)
            val currentTab = tabPositions[currentPage]

            // Tính nextTab tùy offset dương hay âm
            val nextTab = tabPositions.getOrNull(
                if (offset >= 0f) currentPage + 1 else currentPage - 1
            )

            val indicatorOffset = if (nextTab != null) {
                lerp(currentTab.left, nextTab.left, kotlin.math.abs(offset))
            } else {
                currentTab.left
            }

            val indicatorWidth = if (nextTab != null) {
                lerp(currentTab.width, nextTab.width, kotlin.math.abs(offset))
            } else {
                currentTab.width
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomStart)
                    .offset(x = indicatorOffset)
                    .width(indicatorWidth)
                    .height(2.dp)
                    .background(MyColor.Primary)
            )
        }

        val tabRowContent: @Composable () -> Unit = {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            val safeIndex = index.coerceIn(0, tabs.size - 1)
                            pagerState.animateScrollToPage(
                                page = safeIndex,
                                animationSpec = tween(
                                    durationMillis = 200,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    },
                    text = {
                        Text(
                            text = tabTitle(tab),
                            fontSize = 16.sp,
                            fontWeight = W500,
                            style = CoreTypography.displayMedium,
                            color = if (currentPage == index)
                                MyColor.Primary
                            else
                                MyColor.Primary
                        )
                    },
                    modifier = Modifier.height(45.dp)
                        .background(Color.White)
                )
            }
        }

        if (isScrollable) {
            ScrollableTabRow(
                selectedTabIndex = currentPage,
                indicator = indicator,
                edgePadding = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp)
                    .background(Color.White),
            ) {
                tabRowContent()
            }
        } else {
            TabRow(
                selectedTabIndex = currentPage,
                indicator = indicator
            ) {
                tabRowContent()
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                content(tabs[page])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleTabScreenPreview() {
    MaterialTheme {
        val tabs = listOf("All", "Job", "Suggestions", "Other", "Learn")
        AccessedTabView(
            isScrollable = true,
            tabs = tabs,
            selectedTabIndex = 3,
            tabTitle = { it },
            content = { tab ->
                if (tab == tabs[0]){
                } else {
                    Text(tab)
                }
            }
        )
    }
}

