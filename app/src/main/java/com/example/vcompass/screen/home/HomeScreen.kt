package com.example.vcompass.screen.home

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import com.vcompass.presentation.viewmodel.home.HomeViewModel
import com.example.vcompass.screen.connection.ConnectionScreen
import com.example.vcompass.screen.explore.ExploreScreen
import com.example.vcompass.screen.feed.HomeFeedScreen
import com.example.vcompass.screen.search.SearchLandingScreen
import com.example.vcompass.ui.core.general.ScreenWithBottomBar
import com.example.vcompass.ui.core.bottombar.CustomBottomBar
import com.example.vcompass.ui.core.bottombar.bottomDestinations
import com.example.vcompass.util.AppConstants
import com.example.vcompass.util.back
import com.example.vcompass.util.goWithState
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
            navController.goWithState(it)
        }
    }

    fun onBack() {
        navController.back()
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
                AppConstants.HOME_EXPLORE_INDEX -> ExploreScreen(navController)
                AppConstants.HOME_SEARCH_INDEX -> SearchLandingScreen(navController)
                AppConstants.HOME_PROFILE_INDEX -> ConnectionScreen(navController)
            }
        }

        MoreMenuHomeBottomSheet(showMoreMenu.value) {
            showMoreMenu.value = false
        }
    }
}
