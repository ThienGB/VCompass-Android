package com.vcompass.data.repository.login

import com.vcompass.data.local.SecureStorageHelper
import com.vcompass.data.model.dto.party.login.toLoginModel
import com.vcompass.data.remote.services.auth.AuthService
import com.vcompass.data.util.asSingleResultFlow
import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.model.response.login.LoginModel
import com.vcompass.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    val authService: AuthService,
    val secureStorageHelper: SecureStorageHelper
) : LoginRepository {

    override suspend fun login(request: LoginRequest): Flow<Result<LoginModel>> {
        return asSingleResultFlow(
            suspendFunc = {
                val response = authService.login(request)
                secureStorageHelper.accessToken = response.data?.tokens?.accessToken
                secureStorageHelper.userId = response.data?.user?.id
                response
            },
            transform = {
                it.toLoginModel()
            }
        )
    }
}