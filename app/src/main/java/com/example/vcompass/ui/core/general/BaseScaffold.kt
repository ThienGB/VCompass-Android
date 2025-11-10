package com.example.vcompass.ui.core.general

import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.vcompass.core.extensions.conditional
import com.vcompass.core.resource.MyColor

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    statusBarPadding: Boolean = true,
    statusBarContentColor: Color = MyColor.White,
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        modifier = modifier.conditional(statusBarPadding) { statusBarsPadding() },
        topBar = topAppBar,
        contentWindowInsets = WindowInsets(0),
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
//        snackbarHost = {
//            SnackbarHost(
//                snackBarHostState,
//                snackbar = { data ->
//                    CustomSnackBar(data)
//                }
//            )
//        }
    ) { padding ->
        content(padding)
    }
}