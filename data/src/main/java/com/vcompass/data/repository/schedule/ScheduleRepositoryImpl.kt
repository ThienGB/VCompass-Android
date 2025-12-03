package com.vcompass.data.repository.schedule

import com.vcompass.data.model.dto.schedule.toScheduleModel
import com.vcompass.data.remote.services.schedule.ScheduleService
import com.vcompass.data.util.asSingleResultFlow
import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(
    private val scheduleService: ScheduleService
) : ScheduleRepository {
    override suspend fun getScheduleById(id: String): Flow<Result<ScheduleModel>> {
        return asSingleResultFlow(
            suspendFunc = { scheduleService.getScheduleById(id) },
            transform = { dto ->
                dto.toScheduleModel()
            }
        )
    }
}