package com.vcompass.data.remote.services.party

import com.vcompass.data.model.dto.party.login.LoginDTO
import com.vcompass.data.model.response.BaseResponse
import com.vcompass.data.model.response.SingleResponse
import com.vcompass.domain.model.request.login.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/authenticate")
    suspend fun login(@Body loginInfo: LoginRequest): SingleResponse<LoginDTO>

    @POST("api/logout")
    suspend fun logout(): BaseResponse
}