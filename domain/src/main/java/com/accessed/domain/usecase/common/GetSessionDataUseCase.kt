package com.accessed.domain.usecase.common

import com.accessed.domain.model.response.common.SessionDataModel
import com.accessed.domain.repository.common.StatusLoginRepository
import kotlinx.coroutines.flow.Flow

class GetSessionDataUseCase(private val repo: StatusLoginRepository) {
    suspend operator fun invoke(): Flow<Result<SessionDataModel>> {
        return repo.getSessionData()
    }
}