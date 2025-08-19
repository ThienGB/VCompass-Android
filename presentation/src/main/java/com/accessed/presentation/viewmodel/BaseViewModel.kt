package com.accessed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.accessed.presentation.event.global.GlobalConfig
import com.accessed.presentation.event.global.GlobalEvent
import com.accessed.presentation.event.global.GlobalEventBus
import com.accessed.presentation.state.NavigateState
import com.accessed.presentation.state.ViewUIState
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

    fun resetState() {
        _stateUI.value = ViewUIState.Idle
    }

    fun setSuccess() {
        _stateUI.value = ViewUIState.Success
    }

    fun setError(message: String) {
        _stateUI.value = ViewUIState.Error(message)
    }

    fun doNavigate(
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

    fun navigateToLoginScreen() {
        emitGlobal(GlobalEvent.Logout)
    }

    fun addError403() {
        emitGlobal(GlobalEvent.Forbidden)
    }
}
