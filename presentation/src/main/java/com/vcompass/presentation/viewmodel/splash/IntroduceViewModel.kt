package com.vcompass.presentation.viewmodel.splash

import com.vcompass.domain.usecase.common.SetOpenedAppUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel

class IntroduceViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    val setOpenedAppUseCase: SetOpenedAppUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    fun setOpenedApp() {
        collectToState(block = { setOpenedAppUseCase.invoke() })
    }
}