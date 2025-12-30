package com.example.vcompass

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.example.vcompass.model.common.ErrorUiModel
import com.example.vcompass.model.common.getErrorResId
import com.example.vcompass.ui.core.animation.SnackBarFullScreen
import com.example.vcompass.ui.core.general.LoadingBlockScreen
import com.example.vcompass.ui.navigate.AppNavGraph
import com.vcompass.presentation.enums.SnackBarDurationType
import com.vcompass.presentation.enums.SnackBarType
import com.vcompass.presentation.event.global.GlobalEvent
import com.vcompass.presentation.model.meta.SnackBarUiModel
import com.vcompass.presentation.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var showLoading by remember { mutableStateOf(false) }
    var showSnackBar by remember { mutableStateOf(false) }
    var snackBarUiModel by remember { mutableStateOf(SnackBarUiModel()) }

    LaunchedEffect(viewModel) {
        viewModel.globalEvent.events
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { event ->
                when (event) {
                    is GlobalEvent.LoadingGlobal -> {
                        showLoading = event.status
                    }

                    is GlobalEvent.SnackBarInfoGlobal -> {
                        snackBarUiModel = SnackBarUiModel(
                            message = event.msg,
                            type = SnackBarType.INFO,
                            durationType = event.duration,
                            onDismiss = {
                                event.onClick?.invoke()
                            }
                        )
                        showSnackBar = true
                    }

                    is GlobalEvent.SnackBarWarningGlobal -> {
                        snackBarUiModel = SnackBarUiModel(
                            message = event.msg,
                            type = SnackBarType.WARNING,
                            durationType = event.duration,
                            onDismiss = {
                                event.onClick?.invoke()
                            }
                        )
                        showSnackBar = true
                    }

                    is GlobalEvent.SnackBarErrorGlobal -> {
                        val errorUiModel = ErrorUiModel(event.msg)
                        val msgError =
                            errorUiModel.getErrorResId()?.let { context.getString(it) } ?: run {
                                context.getString(R.string.lb_error_other)
                            }

                        snackBarUiModel = SnackBarUiModel(
                            message = msgError,
                            type = SnackBarType.ERROR,
                            durationType = when (event.isRequired) {
                                true -> SnackBarDurationType.NONE
                                else -> event.duration
                            },
                            onDismiss = {
                                viewModel.handleErrorCode(errorUiModel.code) {
                                    event.onClick?.invoke()
                                }
                            }
                        )
                        showSnackBar = true
                    }

                    is GlobalEvent.SnackBarSuccessGlobal -> {
                        snackBarUiModel = SnackBarUiModel(
                            message = event.msg,
                            type = SnackBarType.SUCCESS,
                            durationType = event.duration,
                            onDismiss = {
                                event.onClick?.invoke()
                            }
                        )
                        showSnackBar = true
                    }

                    else -> {}
                }
            }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues()),
        color = MaterialTheme.colorScheme.background
    ) {
        AppNavGraph()
    }
    LoadingBlockScreen(isVisibility = showLoading)
    SnackBarFullScreen(
        snackBarUiModel = snackBarUiModel,
        isVisible = showSnackBar,
    ) {
        viewModel.resetStateGLobal()
        showSnackBar = false
        snackBarUiModel.onDismiss?.invoke()
    }
}