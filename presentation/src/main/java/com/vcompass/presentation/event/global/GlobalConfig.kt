package com.vcompass.presentation.event.global

import com.vcompass.data.local.SecureStorageHelper
import com.vcompass.domain.model.response.common.SessionDataModel

class GlobalConfig(
    private val secureStorageHelper: SecureStorageHelper
) {
    private var sessionDataModel: SessionDataModel? = null

    fun getSessionData() = sessionDataModel

    fun updateSessionData(data: SessionDataModel) {
        if (data.isRememberMe == false)
            data.email = ""
        sessionDataModel = data
    }

    fun userLogout() {
        secureStorageHelper.clearDataAfterLogout()
    }

    fun clearSessionData() {
        sessionDataModel = null
    }
}