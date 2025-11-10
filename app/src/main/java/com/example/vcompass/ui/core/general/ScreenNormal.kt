package com.example.vcompass.ui.core.general

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.example.vcompass.util.back
import com.vcompass.presentation.state.ViewUIState
import com.vcompass.presentation.viewmodel.BaseViewModel

@Composable
fun ScreenNormal(
    modifier: Modifier = Modifier,
    state: ViewUIState? = null,
    viewModel: BaseViewModel,
    navController: NavController,
    statusBarPadding: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val keyboard = LocalSoftwareKeyboardController.current

    BackHandler {
        if (state !is ViewUIState.ShowSnackBar)
            navController.back()
    }

    ObserveGlobalEvents(viewModel, navController)

    BaseScaffold(
        snackBarHostState = snackBarHostState,
        statusBarPadding = statusBarPadding,
        topAppBar = topBar ?: {}
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            KeyboardDismissArea {
                content()
            }
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