package com.vcompass.presentation.event.global

import com.vcompass.domain.model.response.common.SessionDataModel
import com.vcompass.domain.usecase.login.LogoutUseCase

class GlobalConfig(private val logoutUseCase: LogoutUseCase) {
    private var sessionDataModel: SessionDataModel? = null

    fun getSessionData() = sessionDataModel

    fun updateSessionData(data: SessionDataModel) {
        if (data.isRememberMe == false)
            data.userName = ""
        sessionDataModel = data
    }

    suspend fun userLogout() = logoutUseCase()

    fun clearSessionData(){
        sessionDataModel = null
    }
}