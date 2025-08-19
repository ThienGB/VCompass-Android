package com.accessed.domain.repository.login

import com.accessed.domain.model.request.login.LoginRequest
import com.accessed.domain.model.response.login.LoginModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(request: LoginRequest): Flow<Result<LoginModel>>

    suspend fun logout(): Flow<Result<Unit>>
}