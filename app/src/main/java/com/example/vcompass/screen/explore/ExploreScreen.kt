package com.example.vcompass.screen.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.accessed.core.compose_view.text.CoreText
import com.example.vcompass.util.add
import com.vcompass.core.compose_view.AccessedBottomSheet
import com.vcompass.core.compose_view.image.CoreIcon
import com.vcompass.core.resource.MyColor
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography
import com.vcompass.core.typography.CoreTypographySemiBold
import com.vcompass.presentation.util.CoreRoute

@Preview(showSystemUi = true)
@Composable
fun ExploreScreen(
    navController: NavController = rememberNavController(),
) {
    val bottomSheetState = remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    val videos = remember {
        listOf(
            VideoItem(
                id = 1,
                username = "user123",
                description = "Amazing sunset view! 🌅 #sunset #nature #beautiful",
                likes = 1234,
                comments = 89,
                shares = 45,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=1"
            ),
            VideoItem(
                id = 2,
                username = "coolcreator",
                description = "Dance challenge! Who's next? 💃 #dance #viral #challenge",
                likes = 5678,
                comments = 234,
                shares = 123,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=2"
            ),
            VideoItem(
                id = 3,
                username = "foodlover",
                description = "Recipe of the day! Easy and delicious 🍕 #food #recipe #cooking",
                likes = 890,
                comments = 67,
                shares = 34,
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=3"
            ),
            VideoItem(
                id = 4,
                username = "traveler_life",
                description = "Hidden gem in Vietnam 🇻🇳 #travel #vietnam #explore",
                likes = 2341,
                comments = 156,
                shares = 78,
                videoUrl = "https://example.com/video4.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=4"
            ),
            VideoItem(
                id = 5,
                username = "petlover",
                description = "Cutest cat ever! 🐱 #cat #pets #cute #adorable",
                likes = 3456,
                comments = 189,
                shares = 234,
                videoUrl = "https://example.com/video5.mp4",
                thumbnailUrl = "https://picsum.photos/400/700?random=5"
            )
        )
    }

    val followPagerState = rememberPagerState(pageCount = { videos.size })
    val recommendPagerState = rememberPagerState(pageCount = { videos.size })
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(bottom = MyDimen.p56)
            .statusBarsPadding()
    ) {
        ExploreHeaderSection(
            navController = navController,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
        when (selectedTab) {
            0 -> {
                VerticalPager(
                    state = followPagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    VideoPlayerSection(
                        video = videos[page],
                        isActive = followPagerState.currentPage == page,
                        onLikeClick = { /* Handle like */ },
                        onCommentClick = { bottomSheetState.value = true },
                        onShareClick = { /* Handle share */ },
                        onProfileClick = { /* Handle profile click */ }
                    )
                }
            }

            1 -> {
                VerticalPager(
                    state = recommendPagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    VideoPlayerSection(
                        video = videos[page],
                        isActive = recommendPagerState.currentPage == page,
                        onLikeClick = { /* Handle like */ },
                        onCommentClick = { bottomSheetState.value = true },
                        onShareClick = { /* Handle share */ },
                        onProfileClick = { /* Handle profile click */ }
                    )
                }
            }
        }
    }

    AccessedBottomSheet(
        bottomSheetState = bottomSheetState,
        sheetContent = {
            Column {

            }
        }
    )
}

@Composable
fun ExploreHeaderSection(
    navController: NavController,
    selectedTab: Int = 0,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(horizontal = MyDimen.p8),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CoreIcon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            tintColor = MyColor.White,
            iconModifier = Modifier.size(MyDimen.p32)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = MyDimen.p16)
                .weight(1f),
            horizontalArrangement = Arrangement.Center
        ) {
            TransparentTabItem(
                text = "Đang theo dõi",
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
            Spacer(modifier = Modifier.width(MyDimen.p24))
            TransparentTabItem(
                text = "Đề xuất",
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
        }

        CoreIcon(
            imageVector = Icons.Default.Search,
            tintColor = MyColor.White,
            iconModifier = Modifier.size(MyDimen.p28),
            onClick = { navController.add(CoreRoute.ExploreSearchGraph.route) }
        )
    }
}

@Composable
fun TransparentTabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CoreText(
            text = text,
            style = if (selected) CoreTypographySemiBold.labelLarge else CoreTypography.labelLarge,
            color = if (selected) MyColor.White else MyColor.Gray999,
            modifier = Modifier
                .clip(RoundedCornerShape(MyDimen.p6))
                .clickable { onClick() }
                .padding(horizontal = MyDimen.p12, vertical = MyDimen.p6)
        )
        if (selected) {
            HorizontalDivider(
                thickness = MyDimen.p2,
                color = MyColor.White,
                modifier = Modifier
                    .width(MyDimen.p80)
                    .padding(top = MyDimen.p4)
            )
        }

    }
}

data class VideoItem(
    val id: Int,
    val username: String,
    val description: String,
    val likes: Int,
    val comments: Int,
    val shares: Int,
    val videoUrl: String,
    val thumbnailUrl: String
)