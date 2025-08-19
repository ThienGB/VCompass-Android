package com.accessed.presentation.viewmodel.home

import com.accessed.domain.usecase.common.GetSessionDataUseCase
import com.accessed.domain.usecase.login.LogoutUseCase
import com.accessed.presentation.event.global.GlobalConfig
import com.accessed.presentation.event.global.GlobalEventBus
import com.accessed.presentation.util.Screen
import com.accessed.presentation.util.collectToState
import com.accessed.presentation.viewmodel.BaseViewModel

class HomeViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    fun logout() {
        setError("401 / Forbidden")
//        collectToState(
//            block = {
//                logoutUseCase.invoke()
//            }
//        ) {
//            doNavigate(Screen.Login.route)
//        }
    }
}