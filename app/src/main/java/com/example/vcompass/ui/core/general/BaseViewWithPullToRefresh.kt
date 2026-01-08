package com.example.vcompass.ui.core.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.vcompass.resource.MyColor
import com.example.vcompass.ui.core.animation.CoreItemAnimation
import com.example.vcompass.ui.core.loading.CoreErrorItem
import com.example.vcompass.ui.core.loading.CoreLoadMoreItem
import com.vcompass.presentation.state.LoadingType
import com.vcompass.presentation.state.ViewUIState
import com.vcompass.presentation.viewmodel.BaseViewModel

@Composable
fun BaseViewWithPullToRefresh(
    modifier: Modifier = Modifier,
    state: ViewUIState? = null,
    onRefresh: (() -> Unit)? = null,
    viewModel: BaseViewModel,
    navController: NavController,
    statusBarPadding: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(state) {
        pullToRefreshState.animateToHidden()
    }

    BaseView(
        state = state,
        modifier = modifier,
        statusBarPadding = statusBarPadding,
        viewModel = viewModel,
        navController = navController,
        topBar = topBar,
        content = {
            if (state is ViewUIState.Error) {
                CoreErrorItem {
                    onRefresh?.invoke()
                }
                return@BaseView
            }

            CoreItemAnimation(
                isShowSwitchItem = state is ViewUIState.Loading && state.type == LoadingType.INSIDE,
                mainItem = {
                    PullToRefreshBox(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MyColor.White),
                        state = pullToRefreshState,
                        isRefreshing = false,
                        onRefresh = { onRefresh?.invoke() },
                        content = {
                            content.invoke()
                        }
                    )
                },
                switchItem = {
                    CoreLoadMoreItem()
                }
            )
        }
    )
}