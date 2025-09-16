package com.vcompass.data.repository.schedule

import com.vcompass.data.remote.services.schedule.ScheduleService
import com.vcompass.data.util.asSingleResultFlow
import com.vcompass.domain.model.response.schedule.Schedule
import com.vcompass.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(
    private val scheduleService: ScheduleService,) : ScheduleRepository {
    override suspend fun get(id: String): Flow<Result<Schedule>> {
        return asSingleResultFlow(
            suspendFunc = { scheduleService.getScheduleById(id) },
            transform = { dto ->
                dto.toDomain()
            }
        )
    }
}