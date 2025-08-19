package com.accessed.presentation.viewmodel.splash

import com.accessed.domain.usecase.common.SetOpenedAppUseCase
import com.accessed.presentation.event.global.GlobalConfig
import com.accessed.presentation.event.global.GlobalEventBus
import com.accessed.presentation.util.collectToState
import com.accessed.presentation.viewmodel.BaseViewModel

class IntroduceViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    val setOpenedAppUseCase: SetOpenedAppUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    fun setOpenedApp() {
        collectToState(block = { setOpenedAppUseCase.invoke() })
    }
}