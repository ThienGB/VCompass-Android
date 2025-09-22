package com.example.vcompass.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.vcompass.presentation.viewmodel.home.HomeViewModel
import com.example.vcompass.screen.connection.ConnectionScreen
import com.example.vcompass.screen.conversation.ConversationScreen
import com.example.vcompass.screen.feed.HomeFeedScreen
import com.example.vcompass.screen.job.JobLandingScreen
import com.example.vcompass.ui.core.ScreenWithBottomBar
import com.example.vcompass.ui.core.bottombar.CustomBottomBar
import com.example.vcompass.ui.core.bottombar.bottomDestinations
import com.example.vcompass.util.AppConstants
import com.example.vcompass.util.goBack
import com.example.vcompass.util.navigateWithState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val listTabs = remember { bottomDestinations }
    val pagerState = rememberPagerState(initialPage = 0) { listTabs.size }
    val scope = rememberCoroutineScope()
    val showMoreMenu = remember { mutableStateOf(false) }
    val state by viewModel.stateUI.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigate.collect {
            navController.navigateWithState(it)
        }
    }

    fun onBack() {
        navController.goBack()
    }

    ScreenWithBottomBar(
        state = state,
        onBackPress = { onBack() },
        viewModel = viewModel,
        navController = navController,
        bottomBar = {
            CustomBottomBar(
                onClickMenu = {
                    showMoreMenu.value = true
                },
                onClickItemBottomBar = {
                    scope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                }
            )
        },
        onRetry = {
            viewModel.logout()
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false,
            key = { index -> listTabs[index].route }
        ) { page ->
            when (page) {
                AppConstants.HOME_FEED_INDEX -> HomeFeedScreen(navController)
                AppConstants.HOME_JOB_LANDING_INDEX -> JobLandingScreen(navController)
                AppConstants.HOME_CONVERSATION_INDEX -> ConversationScreen(navController)
                AppConstants.HOME_CONNECTION_INDEX -> ConnectionScreen(navController)
            }
        }

        MoreMenuHomeBottomSheet(showMoreMenu.value) {
            showMoreMenu.value = false
        }
    }
}
