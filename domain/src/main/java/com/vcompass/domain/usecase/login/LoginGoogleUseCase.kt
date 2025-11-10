package com.vcompass.domain.usecase.login

import com.vcompass.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginGoogleUseCase(private val repo: LoginRepository) {

    suspend operator fun invoke(): Flow<Result<Unit>> {
        return repo.logout()
    }
}