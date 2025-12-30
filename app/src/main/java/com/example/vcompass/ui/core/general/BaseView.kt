package com.example.vcompass.ui.core.general

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import com.example.vcompass.util.back
import com.vcompass.presentation.state.LoadingType
import com.vcompass.presentation.state.ViewUIState
import com.vcompass.presentation.viewmodel.BaseViewModel

@Composable
fun BaseView(
    modifier: Modifier = Modifier,
    state: ViewUIState? = null,
    viewModel: BaseViewModel? = null,
    navController: NavController? = null,
    bottomBarVisible: Boolean = false,
    statusBarPadding: Boolean = true,
    onBackPress: (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    BackHandler {
        if (state !is ViewUIState.ShowSnackBar)
            onBackPress?.invoke() ?: navController?.back()
    }

    ObserveGlobalEvents(viewModel, navController)

    BaseScaffold(
        modifier = modifier,
        statusBarPadding = statusBarPadding,
        bottomBarVisible = bottomBarVisible,
        bottomBar = bottomBar ?: {},
        topAppBar = topBar ?: {}
    ) {
        KeyboardDismissArea {
            content()
        }
    }

    LaunchedEffect(state) {
        keyboard?.hide()
        focusManager.clearFocus(true)
        if (state is ViewUIState.Loading && state.type == LoadingType.FULL_SCREEN) {
            viewModel?.showLoadingGlobal()
            return@LaunchedEffect
        }
        viewModel?.hideLoadingGlobal()
    }
}