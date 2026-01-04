package com.example.vcompass.screen.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.vcompass.R
import com.example.vcompass.util.rippleClickable
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.list.ScrollDirection
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.ui.pagination.BasePaginationItem
import com.example.vcompass.util.HandleBottomBarOnScroll
import com.vcompass.presentation.viewmodel.feed.HomeFeedViewModel
import com.vcompass.presentation.viewmodel.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeFeedScreen() {
    val viewModel: HomeFeedViewModel = koinViewModel()
    val homeViewModel: HomeViewModel = koinViewModel()
    val stateList = rememberLazyListState()
    stateList.HandleBottomBarOnScroll(
        onScrollUp = { homeViewModel.showBottomBar() },
        onScrollDown = { homeViewModel.hideBottomBar() }
    )
    val schedules = viewModel.schedules.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SpaceHeight(MyDimen.p56)
            BasePaginationItem(
                lazyPagingItems = schedules,
                onScrollDirection = {
                    when (it) {
                        ScrollDirection.Up -> {
                            homeViewModel.showBottomBar()
                        }

                        ScrollDirection.Down -> {
                            homeViewModel.hideBottomBar()
                        }

                        ScrollDirection.Idle -> Unit
                    }
                },
                modifier = Modifier.fillMaxSize(),
                horizontalSpace = MyDimen.p4
            ) { schedule ->
                SchedulePost(schedule)
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .zIndex(1f)
        ) {
            HomeHeader()
        }
    }
}

@Composable
fun HomeHeader() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = MyDimen.p8
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .statusBarsPadding()
                .padding(bottom = MyDimen.p6, end = MyDimen.p16)
        ) {
            // Logo with better scaling
            Image(
                painter = painterResource(R.drawable.logo_vcompass),
                contentDescription = "VCompass Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(MyDimen.p40)
                    .width(MyDimen.p160)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Action buttons with ripple effects
            Row(horizontalArrangement = Arrangement.spacedBy(MyDimen.p12)) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .rippleClickable {}
                        .size(MyDimen.p36)
                        .background(
                            color = MyColor.GrayF5,
                            shape = CircleShape
                        )
                ) {
                    CoreIcon(
                        resDrawable = R.drawable.ic_notifications_black_24dp,
                        iconModifier = Modifier.size(MyDimen.p20),
                        tintColor = MyColor.Gray333
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .rippleClickable {}
                        .size(MyDimen.p36)
                        .background(
                            color = MyColor.GrayF5,
                            shape = CircleShape
                        )
                ) {
                    CoreIcon(
                        resDrawable = R.drawable.ic_home_conversation_fill,
                        iconModifier = Modifier.size(MyDimen.p20),
                        tintColor = MyColor.Gray333
                    )
                }
            }
        }
    }
}



