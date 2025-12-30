package com.example.vcompass.screen.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.util.rippleClickable
import com.example.vcompass.ui.core.icon.CoreIcon
import com.vcompass.core.compose_view.list.VerticalList
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.example.vcompass.ui.core.list.ScrollDirection
import com.example.vcompass.ui.core.list.rememberVerticalScrollDirection
import com.vcompass.presentation.viewmodel.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeFeedScreen(
    navController: NavController
) {
    val homeViewModel = koinViewModel<HomeViewModel>()
    val stateList = rememberLazyListState()
    val scrollDirection by rememberVerticalScrollDirection(stateList)
    val listItems = remember { (1..10).toList() }

    LaunchedEffect(scrollDirection) {
        when (scrollDirection) {
            ScrollDirection.Up -> {
                homeViewModel.showBottomBar()
            }

            ScrollDirection.Down -> {
                homeViewModel.hideBottomBar()
            }

            ScrollDirection.Idle -> {}
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        VerticalList(
            items = listItems,
            state = stateList,
            contentPadding = PaddingValues(
                top = MyDimen.p72,
                bottom = MyDimen.p56
            ),
            verticalArrangement = Arrangement.spacedBy(MyDimen.p4),
            modifier = Modifier.fillMaxSize()
        ) {
            TravelPost(navController)
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



