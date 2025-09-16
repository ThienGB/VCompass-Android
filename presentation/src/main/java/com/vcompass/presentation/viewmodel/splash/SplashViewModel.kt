package com.vcompass.presentation.viewmodel.splash

import com.vcompass.domain.model.response.common.isLogged
import com.vcompass.domain.usecase.common.GetSessionDataUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel
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