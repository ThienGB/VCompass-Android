package com.accessed.domain.repository.schedule

import com.accessed.domain.model.request.login.LoginRequest
import com.accessed.domain.model.response.login.LoginModel
import com.accessed.domain.model.response.schedule.Schedule
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.Async

interface ScheduleRepository {
    suspend fun get(id: String): Flow<Result<Schedule>>
}