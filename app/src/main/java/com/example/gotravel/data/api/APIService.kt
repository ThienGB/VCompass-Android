package com.example.gotravel.data.api

import com.example.gotravel.data.api.model.ScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/schedule/mobile/{id}")
    suspend fun getScheduleById(
        @Path("id") id: String
    ): ScheduleResponse
}