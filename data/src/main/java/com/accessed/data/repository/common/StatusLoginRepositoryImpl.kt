package com.accessed.data.repository.common

import com.accessed.data.local.SecureStorageHelper
import com.accessed.data.util.actionIfNotNull
import com.accessed.data.util.asResultFlow
import com.accessed.data.util.asUnitResultFlow
import com.accessed.domain.model.response.common.SessionDataModel
import com.accessed.domain.repository.common.StatusLoginRepository
import kotlinx.coroutines.flow.Flow

class StatusLoginRepositoryImpl(val secureStorageHelper: SecureStorageHelper) :
    StatusLoginRepository {

    override suspend fun getSessionData(): Flow<Result<SessionDataModel>> {
        val sessionDataModel = SessionDataModel()
        secureStorageHelper.apply {
            sessionDataModel.let {
                it.isOpenedApp = isOpenedApp ?: false
                it.messengerId = messengerId
                it.isRememberMe = isRememberMe
                it.accessToken = accessToken
                it.calendarId = calendarId
                it.calendarUserId = calendarUserId
                it.isOpenedApp = isOpenedApp
            }
        }
        return sessionDataModel.asResultFlow()
    }

    override suspend fun setOpenedApp(): Flow<Result<Unit>> {
        secureStorageHelper.isOpenedApp = true
        return asUnitResultFlow()
    }
}