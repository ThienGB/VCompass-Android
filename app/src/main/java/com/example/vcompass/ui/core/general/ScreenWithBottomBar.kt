package com.example.vcompass.ui.core.general

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.vcompass.presentation.state.ViewUIState
import com.vcompass.presentation.viewmodel.BaseViewModel

@Composable
fun ScreenWithBottomBar(
    state: ViewUIState,
    viewModel: BaseViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val snackBarHostState = remember { SnackbarHostState() }

    ObserveGlobalEvents(viewModel, navController)

    BaseScaffold(
        snackBarHostState = snackBarHostState,
        topAppBar = topAppBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            KeyboardDismissArea { content() }
        }
    }

    LaunchedEffect(state) {
        keyboard?.hide()
        when (state) {
            is ViewUIState.Loading -> viewModel.showLoadingGlobal()
            else -> {
                viewModel.hideLoadingGlobal()
            }
        }
    }
}

