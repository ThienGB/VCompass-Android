package com.accessed.data.remote.services.schedule

import com.accessed.data.model.dto.schedule.ScheduleDTO
import com.accessed.data.model.response.SingleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ScheduleService {
    @GET("api/schedule/mobile/{id}")
    suspend fun getScheduleById(
        @Path("id") id: String
    ): SingleResponse<ScheduleDTO>
}