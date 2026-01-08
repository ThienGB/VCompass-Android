package com.vcompass.presentation.viewmodel.login

import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.model.response.user.UserModel
import com.vcompass.domain.usecase.login.LoginGoogleUseCase
import com.vcompass.domain.usecase.login.LoginUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.model.login.toLoginUiModel
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LoginViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val loginUseCase: LoginUseCase,
    private val loginGoogleUseCase: LoginGoogleUseCase,
) : BaseViewModel(globalEventBus, globalConfig) {

    private val _notYetConfirm = MutableSharedFlow<Boolean>(0, 1)
    var notYetConfirm = _notYetConfirm.asSharedFlow()

    private val _registerSuccess = MutableSharedFlow<Boolean>(0, 1)
    var registerSuccess = _registerSuccess.asSharedFlow()

    fun getRememberMe(onRemember: (String, Boolean) -> Unit) {
        globalConfig.getSessionData()?.let {
            onRemember.invoke(it.email ?: "", it.isRememberMe ?: false)
        }
    }

    fun login(
        email: String,
        password: String,
        hasRemember: Boolean = true,
        onSuccess: () -> Unit = {}
    ) {
        collectToState(
            block = {
                loginUseCase(LoginRequest(email = email, password = password))
            },
            onError = {
                showError(it.message ?: "")
            }
        ) { login ->
            val loginModel = login.toLoginUiModel()
            globalConfig.getSessionData()?.let {
                it.isRememberMe = hasRemember
                it.email = email
                it.accessToken = loginModel.tokens?.accessToken
                it.currentUser = login.user ?: UserModel()
                globalConfig.updateSessionData(it)
            }
            onSuccess()
        }
    }

    fun loginGoogle() {
        collectToState(
            block = { loginGoogleUseCase() }
        ) {
            globalConfig.getSessionData()?.let {
                it.isRememberMe = false
                globalConfig.updateSessionData(it)
            }
            navigateTo(CoreRoute.Home.route, isClearStack = true)
        }
    }
}