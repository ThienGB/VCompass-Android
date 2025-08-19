package com.accessed.domain.usecase.login

import com.accessed.domain.model.request.login.LoginRequest
import com.accessed.domain.model.response.login.LoginModel
import com.accessed.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repo: LoginRepository) {
    suspend operator fun invoke(request: LoginRequest): Flow<Result<LoginModel>> {
        return repo.login(request)
    }
}