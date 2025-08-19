package com.accessed.presentation.viewmodel.login

import com.accessed.domain.model.request.login.LoginRequest
import com.accessed.domain.usecase.common.GetSessionDataUseCase
import com.accessed.domain.usecase.login.LoginUseCase
import com.accessed.presentation.event.global.GlobalConfig
import com.accessed.presentation.event.global.GlobalEventBus
import com.accessed.presentation.util.Screen
import com.accessed.presentation.util.collectToState
import com.accessed.presentation.viewmodel.BaseViewModel

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
            doNavigate(Screen.Home.route, isClearStack = true)
        }
    }
}