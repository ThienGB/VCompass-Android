package com.vcompass.data.remote.services

import com.vcompass.data.model.dto.party.person.PersonDTO
import com.vcompass.data.model.response.SingleResponse
import retrofit2.http.GET

interface UserInfoService {
    @GET("api/user/current")
    suspend fun getCurrentUser(): SingleResponse<PersonDTO>
}