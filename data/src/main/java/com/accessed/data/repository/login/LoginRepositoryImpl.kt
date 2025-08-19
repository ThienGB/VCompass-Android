package com.accessed.data.repository.login

import com.accessed.data.local.SecureStorageHelper
import com.accessed.data.remote.services.message.FcmService
import com.accessed.data.remote.services.party.AuthService
import com.accessed.data.util.api_call.ApiCallResult
import com.accessed.data.util.asMultipleResultFlow
import com.accessed.data.util.asSingleResultFlow
import com.accessed.domain.model.request.login.LoginRequest
import com.accessed.domain.model.response.login.LoginModel
import com.accessed.domain.repository.login.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl(
    val authService: AuthService,
    val fcmService: FcmService,
    val secureStorageHelper: SecureStorageHelper
) : LoginRepository {

    override suspend fun login(request: LoginRequest): Flow<Result<LoginModel>> {
        return asSingleResultFlow(
            suspendFunc = {
                val response = authService.login(request)
                secureStorageHelper.accessToken = response.data?.token
                response
            },
            transform = { dto ->
                dto.toDomain()
            }
        )
    }

    override suspend fun logout(): Flow<Result<Unit>> = flow {
        asMultipleResultFlow {
            unit { authService.logout() }
            unit { fcmService.removeFCMToken(secureStorageHelper.messengerId ?: "") }
        }.collect {
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