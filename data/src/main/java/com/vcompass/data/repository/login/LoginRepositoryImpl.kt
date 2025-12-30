package com.vcompass.data.repository.login

import com.vcompass.data.local.SecureStorageHelper
import com.vcompass.data.model.dto.party.login.toLoginModel
import com.vcompass.data.remote.services.auth.AuthService
import com.vcompass.data.util.api_call.ApiCallResult
import com.vcompass.data.util.asMultipleResultFlow
import com.vcompass.data.util.asSingleResultFlow
import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.model.response.login.LoginModel
import com.vcompass.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override suspend fun logout(): Flow<Result<Unit>> = flow {
        asMultipleResultFlow { unit { authService.logout() } }.collect {
            when (it) {
                is ApiCallResult.Failure -> {
                    emit(Result.failure(it.error))
                }

                is ApiCallResult.Success -> {
                    emit(Result.success(Unit))
                }

                is ApiCallResult.Done -> {
                    secureStorageHelper.clearDataAfterLogout()
                    emit(Result.success(Unit))
                }
            }
        }
    }
}