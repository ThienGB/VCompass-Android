package com.accessed.data.remote.services.party

import com.accessed.data.model.dto.party.person.PersonDTO
import com.accessed.data.model.response.SingleResponse
import retrofit2.http.GET

interface UserInfoService {
    @GET("api/user/current")
    suspend fun getCurrentUser(): SingleResponse<PersonDTO>
}