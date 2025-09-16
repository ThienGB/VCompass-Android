package com.vcompass.domain.usecase.common

import com.vcompass.domain.model.response.common.SessionDataModel
import com.vcompass.domain.repository.common.StatusLoginRepository
import kotlinx.coroutines.flow.Flow

class GetSessionDataUseCase(private val repo: StatusLoginRepository) {
    suspend operator fun invoke(): Flow<Result<SessionDataModel>> {
        return repo.getSessionData()
    }
}