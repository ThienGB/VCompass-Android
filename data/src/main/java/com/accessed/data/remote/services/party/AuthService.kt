package com.accessed.data.remote.services.party

import com.accessed.data.model.dto.party.login.LoginDTO
import com.accessed.data.model.response.BaseResponse
import com.accessed.data.model.response.SingleResponse
import com.accessed.domain.model.request.login.LoginRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/authenticate")
    suspend fun login(@Body loginInfo: LoginRequest): SingleResponse<LoginDTO>

    @POST("api/logout")
    suspend fun logout(): BaseResponse
}