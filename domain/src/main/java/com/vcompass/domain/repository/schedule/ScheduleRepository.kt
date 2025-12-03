package com.vcompass.domain.repository.schedule

import com.vcompass.domain.model.response.schedule.ScheduleModel
import kotlinx.coroutines.flow.Flow

interface   ScheduleRepository {
    suspend fun getScheduleById(id: String): Flow<Result<ScheduleModel>>
}