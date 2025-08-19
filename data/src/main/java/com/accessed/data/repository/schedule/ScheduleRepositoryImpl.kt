package com.accessed.data.repository.schedule

import com.accessed.data.remote.services.schedule.ScheduleService
import com.accessed.data.util.asSingleResultFlow
import com.accessed.domain.model.response.schedule.Schedule
import com.accessed.domain.repository.schedule.ScheduleRepository
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