package com.example.vcompass.ui.core.general

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vcompass.core.compose_view.snack_bar.CustomSnackBar

@Composable
fun BaseScaffold(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = topAppBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,floatingActionButtonPosition = FabPosition.Center,
        snackbarHost = {
            SnackbarHost(
                snackBarHostState,
                snackbar = { data ->
                    CustomSnackBar(data)
                }
            )
        }
    ) { padding ->
        content(padding)
    }
}