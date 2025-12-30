package com.vcompass.domain.repository.login

import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.model.response.login.LoginModel
import com.vcompass.domain.model.response.user.UserModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(request: LoginRequest): Flow<Result<LoginModel>>

    suspend fun logout(): Flow<Result<Unit>>
}