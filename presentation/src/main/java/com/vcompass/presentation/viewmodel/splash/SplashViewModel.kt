package com.vcompass.presentation.viewmodel.splash

import com.vcompass.domain.model.response.common.isLogged
import com.vcompass.domain.usecase.common.GetSessionDataUseCase
import com.vcompass.presentation.enums.StatusOpenApp
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SplashViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val sessionUseCase: GetSessionDataUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    private val _statusOpenApp = MutableStateFlow(StatusOpenApp.NONE)
    val statusOpenApp = _statusOpenApp.asStateFlow()

    init {
        collectToState(block = { sessionUseCase() }) {
            globalConfig.updateSessionData(it)
            _statusOpenApp.value = when {
                it.isOpenedApp == false -> StatusOpenApp.INTRODUCE
                it.isLogged() -> StatusOpenApp.LOGGED
                else -> StatusOpenApp.GUEST
            }
        }
    }
}