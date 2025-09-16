package com.vcompass.domain.usecase.common

import com.vcompass.domain.repository.common.StatusLoginRepository
import kotlinx.coroutines.flow.Flow

class SetOpenedAppUseCase(private val repo: StatusLoginRepository) {
    suspend operator fun invoke(): Flow<Result<Unit>> {
        return repo.setOpenedApp()
    }
}