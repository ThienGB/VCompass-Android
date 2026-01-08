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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.vcompass.screen.explore.ExploreScreen
import com.example.vcompass.screen.feed.HomeFeedScreen
import com.example.vcompass.screen.profile.MyProfileScreen
import com.example.vcompass.screen.search.SearchLandingScreen
import com.example.vcompass.ui.core.bottombar.CustomBottomBar
import com.example.vcompass.ui.core.bottombar.bottomDestinations
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.util.AppConstants
import com.example.vcompass.util.goWithState
import com.vcompass.presentation.viewmodel.home.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val listTabs = remember { bottomDestinations }
    var bottomBarVisible by rememberSaveable { mutableStateOf(true) }
    val pagerState = rememberPagerState(initialPage = 0) { listTabs.size }
    val scope = rememberCoroutineScope()
    val showMoreMenu = remember { mutableStateOf(false) }
    val state by viewModel.stateUI.collectAsState()
    var statusBarPadding by rememberSaveable() { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.navigate.collect {
            navController.goWithState(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.bottomBarVisible.collect {
            bottomBarVisible = it
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        statusBarPadding = pagerState.currentPage != 4
    }

    BaseView(
        state = state,
        viewModel = viewModel,
        bottomBarVisible = bottomBarVisible,
        navController = navController,
        statusBarPadding = statusBarPadding,
        bottomBar = {
            CustomBottomBar(
                onClickMenu = {
                    showMoreMenu.value = true
                },
                onClickItemBottomBar = {
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
            )
        }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = false,
            key = { index -> listTabs[index].route }
        ) { page ->
            when (page) {
                AppConstants.HOME_FEED_INDEX -> HomeFeedScreen()
                AppConstants.HOME_EXPLORE_INDEX -> ExploreScreen(navController)
                AppConstants.HOME_SEARCH_INDEX -> SearchLandingScreen(navController)
                AppConstants.HOME_PROFILE_INDEX -> MyProfileScreen()
            }
        }

        MoreMenuHomeBottomSheet(showMoreMenu.value) {
            showMoreMenu.value = false
        }
    }
}
