package com.vcompass.data.remote.services.schedule

import com.vcompass.data.model.dto.schedule.ScheduleDTO
import com.vcompass.data.model.response.SingleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleService {
    @GET("api/v1/schedule/{id}")
    suspend fun getScheduleById(
        @Path("id") id: String
    ): SingleResponse<ScheduleDTO>
}