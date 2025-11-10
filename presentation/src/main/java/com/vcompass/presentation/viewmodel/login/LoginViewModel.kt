package com.vcompass.presentation.viewmodel.login

import com.vcompass.data.model.response.GenericException
import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.usecase.login.LoginGoogleUseCase
import com.vcompass.domain.usecase.login.LoginUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import java.io.File

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
            onRemember.invoke(it.userName ?: "", it.isRememberMe ?: false)
        }
    }

    fun login(username: String, password: String, hasRememberMe: Boolean = true) {
        collectToState(
            block = {
                loginUseCase(
                    LoginRequest(username = username, password = password)
                )
            },
            onError = {
                (it as? GenericException.ForbiddenException)?.let { exception ->
                    exception.getData()?.let { data ->
                        val email = JSONObject(data.toString()).get("email").toString()
                        globalConfig.getSessionData()?.currentUser?.email = email
                        _notYetConfirm.tryEmit(true)
                        setSuccess()
                    }
                } ?: showError(it.message ?: "")
            }
        ) {
            globalConfig.getSessionData()?.let {
                it.isRememberMe = hasRememberMe
                it.userName = username
                globalConfig.updateSessionData(it)
            }
            navigateTo(CoreRoute.Home.route, isClearStack = true)
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