package com.vcompass.domain.repository.schedule

import com.vcompass.domain.model.request.login.LoginRequest
import com.vcompass.domain.model.response.login.LoginModel
import com.vcompass.domain.model.response.schedule.Schedule
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.Async

interface ScheduleRepository {
    suspend fun get(id: String): Flow<Result<Schedule>>
}