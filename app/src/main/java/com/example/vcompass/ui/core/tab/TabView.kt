package com.example.vcompass.ui.core.tab

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.lerp
import com.example.vcompass.ui.core.text.CoreText
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.resource.CoreTypographyMedium
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun <T> TabView(
    modifier: Modifier = Modifier,
    tabs: List<T>,
    selectedTabIndex: Int = 0,
    isScrollable: Boolean = false,
    onTabSelected: (Int) -> Unit = {},
    tabTitle: @Composable (T) -> String,
    content: @Composable (T) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { tabs.size})
    val coroutineScope = rememberCoroutineScope()
    val currentPage by remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(currentPage) {
        onTabSelected(currentPage)
    }
    LaunchedEffect(selectedTabIndex) {
        if (selectedTabIndex in tabs.indices && selectedTabIndex != pagerState.currentPage) {
            pagerState.scrollToPage(selectedTabIndex)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        val currentPage = pagerState.currentPage
        val indicator: @Composable (List<TabPosition>) -> Unit = { tabPositions ->
            val currentPage = pagerState.currentPage
            val offset = pagerState.currentPageOffsetFraction.coerceIn(-1f, 1f)
            val currentTab = tabPositions[currentPage]
            val nextTab = tabPositions.getOrNull(
                if (offset >= 0f) currentPage + 1 else currentPage - 1
            )
            val indicatorOffset = if (nextTab != null) {
                lerp(currentTab.left, nextTab.left, abs(offset))
            } else {
                currentTab.left
            }
            val indicatorWidth = if (nextTab != null) {
                lerp(currentTab.width, nextTab.width, abs(offset))
            } else {
                currentTab.width
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.BottomStart)
                    .offset(x = indicatorOffset)
                    .width(indicatorWidth)
                    .height(MyDimen.p2)
                    .background(MyColor.Primary)
            )
        }

        val tabRowContent: @Composable () -> Unit = {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(page = index)
                            onTabSelected(index)
                        }
                    },
                    text = {
                        CoreText(
                            text = tabTitle(tab),
                            style = CoreTypographyMedium.labelMedium,
                            color = if (currentPage == index)
                                MyColor.Primary
                            else
                                MyColor.TextColorPrimary
                        )
                    },
                    modifier = Modifier.height(MyDimen.p40)
                        .background(MyColor.White)
                )
            }
        }

        if (isScrollable) {
            ScrollableTabRow(
                selectedTabIndex = currentPage,
                indicator = indicator,
                edgePadding = MyDimen.zero,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColor.White),
            ) {
                tabRowContent()
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(MyDimen.p8)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(MyColor.GrayF5, MyColor.White)
                        )
                    )
            )
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
fun TabScreenPreview() {
    MaterialTheme {
        val tabs = listOf("All", "Job", "Suggestions", "Other", "Learn")
        TabView(
            isScrollable = true,
            tabs = tabs,
            selectedTabIndex = 3,
            tabTitle = { it },
            onTabSelected = {},
            content = { tab ->
                if (tab == tabs[0]) {
                    Text(tab)
                }
            }
        )
    }
}



