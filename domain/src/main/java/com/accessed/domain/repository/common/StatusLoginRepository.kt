package com.accessed.domain.repository.common

import com.accessed.domain.model.response.common.SessionDataModel
import kotlinx.coroutines.flow.Flow

interface StatusLoginRepository {
    suspend fun getSessionData(): Flow<Result<SessionDataModel>>
    suspend fun setOpenedApp(): Flow<Result<Unit>>
}