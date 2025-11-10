package com.vcompass.presentation.viewmodel.home

import com.vcompass.domain.usecase.login.LogoutUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.viewmodel.BaseViewModel

class HomeViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val logoutUseCase: LogoutUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    fun logout() {
//        collectToState(
//            block = {
//                logoutUseCase.invoke()
//            }
//        ) {
//            doNavigate(Screen.Login.route)
//        }
    }
}