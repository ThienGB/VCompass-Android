package com.accessed.presentation.viewmodel.splash

import com.accessed.domain.model.response.common.isLogged
import com.accessed.domain.usecase.common.GetSessionDataUseCase
import com.accessed.presentation.event.global.GlobalConfig
import com.accessed.presentation.event.global.GlobalEventBus
import com.accessed.presentation.util.collectToState
import com.accessed.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SplashViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val sessionUseCase: GetSessionDataUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    private val _isLogged = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> get() = _isLogged

    private val _isOpenedApp = MutableStateFlow(false)
    val isOpenedApp: StateFlow<Boolean> get() = _isOpenedApp

    init {
        collectToState(block = {
            sessionUseCase.invoke()
        }) {
            globalConfig.setSessionData(it)
            _isOpenedApp.value = it.isOpenedApp ?: false
            _isLogged.value = it.isLogged()
        }
    }
}