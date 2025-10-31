package com.vcompass.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEvent
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.state.NavigateState
import com.vcompass.presentation.state.ViewUIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

open class BaseViewModel(
    val globalEventBus: GlobalEventBus,
    val globalConfig: GlobalConfig,
) : ViewModel() {

    private val _stateUI = MutableStateFlow<ViewUIState>(ViewUIState.Idle)
    val stateUI: StateFlow<ViewUIState> get() = _stateUI

    private val _navigate = Channel<NavigateState>()
    val navigate = _navigate.receiveAsFlow()

    protected fun emitGlobal(event: GlobalEvent) {
        globalEventBus.tryEmit(event)
    }

    protected suspend fun emitGlobalSync(event: GlobalEvent) {
        globalEventBus.emit(event)
    }

    fun setLoading() {
        _stateUI.value = ViewUIState.Loading
    }

    fun hideLoadingGlobal() {
        emitGlobal(GlobalEvent.LoadingGlobal(false))
    }

    fun showLoadingGlobal() {
        emitGlobal(GlobalEvent.LoadingGlobal(true))
    }

    private fun showSnackBar(){
        _stateUI.value = ViewUIState.ShowSnackBar
    }

    fun resetStateLocal() {
        _stateUI.value = ViewUIState.Idle
    }

    fun resetStateGLobal() {
        emitGlobal(GlobalEvent.Idle)
    }

    fun forceUpdateEvent() {
        emitGlobal(GlobalEvent.ForceUpdate)
    }

    fun setSuccess() {
        _stateUI.value = ViewUIState.Success
    }

    fun showError(
        message: String,
        duration: SnackBarDurationType = SnackBarDurationType.SHORT,
        onClick: (() -> Unit)? = null
    ) {
        showSnackBar()
        emitGlobal(
            GlobalEvent.SnackBarErrorGlobal(
                message, duration, onClick
            )
        )
    }

    fun showToastSuccess(
        message: String,
        duration: SnackBarDurationType = SnackBarDurationType.SHORT,
        onClick: (() -> Unit)? = null
    ) {
        showSnackBar()
        emitGlobal(
            GlobalEvent.SnackBarSuccessGlobal(
                message, duration, onClick
            )
        )
    }

    fun showToast(
        message: String,
        duration: SnackBarDurationType = SnackBarDurationType.SHORT,
        onClick: (() -> Unit)? = null
    ) {
        showSnackBar()
        emitGlobal(
            GlobalEvent.SnackBarInfoGlobal(
                message, duration, onClick
            )
        )
    }

    fun showToastWarning(
        message: String,
        duration: SnackBarDurationType = SnackBarDurationType.SHORT, onClick: (() -> Unit)? = null
    ) {
        showSnackBar()
        emitGlobal(
            GlobalEvent.SnackBarWarningGlobal(
                message, duration, onClick
            )
        )
    }

    fun navigateTo(
        route: String,
        isClearStack: Boolean = false,
        isReplace: Boolean = false
    ) {
        viewModelScope.launch {
            _navigate.send(
                NavigateState.AllowNavigate(
                    route,
                    isClearStack = isClearStack,
                    isReplace = isReplace
                )
            )
        }
    }

    fun globalLogout() {
        if (globalConfig.getSessionData()?.accessToken?.isNotEmpty() == true)
            emitGlobal(GlobalEvent.Logout)
    }

    fun handleExpiredToken() {
        collectToState(
            showLoading = false,
            block = {
                globalConfig.userLogout()
            },
            onError = {
                globalConfig.clearSessionData()
            }
        ) {
            globalConfig.clearSessionData()
        }
    }

    open fun onForceUpdate() {

    }

    fun handleErrorCode(errorCode: Int?, otherClick: (() -> Unit)? = null) {
        when {
            errorCode?.isExpiredToken() == true -> {
                globalLogout()
            }

            errorCode?.isForbidden() == true -> {
                emitGlobal(GlobalEvent.Forbidden)
            }

            else -> {
                otherClick?.invoke()
            }
        }
    }
}