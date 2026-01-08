package com.vcompass.data.remote.services.auth

import com.vcompass.data.model.dto.party.login.LoginDTO
import com.vcompass.data.model.response.BaseResponse
import com.vcompass.data.model.response.SingleResponse
import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.model.request.login.RefreshTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/auth/login")
    suspend fun login(@Body loginInfo: LoginRequest): SingleResponse<LoginDTO>

    @POST("api/v1/auth/refresh-token")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenRequest): SingleResponse<LoginDTO>
}