package com.vcompass.data.remote.services.schedule

import com.vcompass.data.model.dto.schedule.ScheduleDTO
import com.vcompass.data.model.response.paging.PagingResponse
import com.vcompass.data.model.response.SingleResponse
import com.vcompass.data.util.DataConstants.DEFAULT_PAGE_SIZE
import com.vcompass.data.util.DataConstants.DEFAULT_PAGE_SIZE_20
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleService {
    @GET("api/v1/schedules/{id}")
    suspend fun getScheduleDetail(
        @Path("id") id: String
    ): SingleResponse<ScheduleDTO>

    @GET("api/v1/schedules?limit=$DEFAULT_PAGE_SIZE&includeInvited=false&sortDir=desc")
    suspend fun getAllSchedules(
        @Query("page") page: Int
    ): SingleResponse<PagingResponse<ScheduleDTO>>
}