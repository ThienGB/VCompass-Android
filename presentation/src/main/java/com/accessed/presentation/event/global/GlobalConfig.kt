package com.accessed.presentation.event.global

import com.accessed.domain.model.response.common.SessionDataModel
import com.accessed.domain.model.response.common.add403
import com.accessed.domain.repository.login.LoginRepository
import com.accessed.presentation.util.PresentationConstants

class GlobalConfig(private val loginRepo: LoginRepository) {
    private var sessionDataModel: SessionDataModel? = null

    fun setSessionData(data: SessionDataModel) {
        sessionDataModel = data
    }

    fun add403(onOutOfLimit: () -> Unit) {
        sessionDataModel?.add403(PresentationConstants.LIMIT_403) {
            onOutOfLimit.invoke()
        }
    }

    suspend fun clearSessionData() {
        loginRepo.logout().collect {  }
    }
}