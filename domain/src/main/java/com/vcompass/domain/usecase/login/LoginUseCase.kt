package com.vcompass.domain.usecase.login

import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.model.response.login.LoginModel
import com.vcompass.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repo: LoginRepository) {
    suspend operator fun invoke(request: LoginRequest): Flow<Result<LoginModel>> {
        return repo.login(request)
    }
}