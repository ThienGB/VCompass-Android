package com.example.vcompass.ui.core.general

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.vcompass.core.compose_view.snack_bar.CustomSnackBarVisuals
import com.vcompass.core.enums.SnackBarType
import com.vcompass.data.model.response.BaseResponse
import com.vcompass.presentation.state.ViewUIState
import com.vcompass.presentation.viewmodel.BaseViewModel
import com.example.vcompass.R
import com.example.vcompass.model.common.ErrorUiModel
import com.example.vcompass.model.common.getErrorResId
import com.example.vcompass.util.handleErrorCode
import com.example.vcompass.util.ifDebug
import com.example.vcompass.util.isExpiredToken

@Composable
fun ScreenNormal(
    state: ViewUIState,
    viewModel: BaseViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    textRetry: String? = null,
    onRetry: (() -> Unit)? = null,
    onBackPress: () -> Unit = {},
    content: @Composable () -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val isShowBlockBackground = remember { mutableStateOf(false) }

    val context = LocalContext.current

    val errorText =
        when {
            onRetry != null -> stringResource(R.string.app_name)
            textRetry != null -> textRetry
            else -> stringResource(R.string.app_name)
        }

    BackHandler {
        onBackPress.invoke()
    }

    ObserveGlobalEvents(viewModel, navController)

    BaseScaffold(snackBarHostState = snackBarHostState) { padding ->
        Box(modifier = modifier.fillMaxSize()) {
            DismissKeyboardOnClickOutside { content() }
            LoadingBlockScreen(
                isVisibility = isShowBlockBackground.value,
                isLoading = state is ViewUIState.Loading
            )
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is ViewUIState.Loading -> {
                isShowBlockBackground.value = true
            }

            is ViewUIState.Error -> {
                val errorUiModel = ErrorUiModel(state.message)
                val msgError = errorUiModel.getErrorResId()?.let { context.getString(it) }

                if (errorUiModel.code?.isExpiredToken() == true)
                    isShowBlockBackground.value = true

                snackBarHostState.showSnackbar(
                    CustomSnackBarVisuals(
                        type = SnackBarType.ERROR,
                        message = msgError?.ifDebug { errorUiModel.message ?: "" } ?: "",
                        actionLabel = errorText,
                        duration = SnackbarDuration.Short
                    )
                ).let {
                    if (it == SnackbarResult.ActionPerformed)
                        when (errorUiModel.code) {
                            BaseResponse.ERROR_CODE_UNAUTHORIZED -> {}

                            else -> onRetry?.invoke()
                        }
                }

                handleErrorCode(errorUiModel.code, viewModel)

                isShowBlockBackground.value = false
                viewModel.resetState()
            }

            else -> {}
        }
    }
}