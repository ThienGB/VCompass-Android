package com.vcompass.presentation.viewmodel.login

import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.usecase.login.LoginUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel

class LoginViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    fun login(email: String, password: String) {
        collectToState(
            block = {
                loginUseCase(LoginRequest(email, password))
            },
        ) {
            doNavigate(CoreRoute.Home.route, isClearStack = true)
        }
    }
}