package com.vcompass.presentation.event.global

import com.vcompass.domain.model.response.common.SessionDataModel
import com.vcompass.domain.model.response.common.add403
import com.vcompass.domain.repository.login.LoginRepository
import com.vcompass.presentation.util.PresentationConstants

class GlobalConfig(private val logoutUseCase: LogoutUseCase) {
    private var sessionDataModel: SessionDataModel? = null

    fun getSessionData() = sessionDataModel

    fun updateSessionData(data: SessionDataModel) {
        if (data.isRememberMe == false)
            data.userName = ""
        sessionDataModel = data
    }

    fun add403(onOutOfLimit: () -> Unit) {
        sessionDataModel?.add403(PresentationConstants.LIMIT_403) {
            onOutOfLimit.invoke()
        }
    }

    suspend fun userLogout() = logoutUseCase()

    fun clearSessionData(){
        sessionDataModel = null
    }
}