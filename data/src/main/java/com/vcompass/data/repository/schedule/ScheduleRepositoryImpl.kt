package com.vcompass.data.repository.schedule

import androidx.paging.PagingData
import com.vcompass.data.model.dto.schedule.toScheduleModel
import com.vcompass.data.remote.services.ScheduleService
import com.vcompass.data.util.asSingleResultFlow
import com.vcompass.data.util.createPagingFlow
import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class ScheduleRepositoryImpl(
    private val scheduleService: ScheduleService
) : ScheduleRepository {
    override suspend fun getScheduleDetail(id: String): Flow<Result<ScheduleModel>> {
        return asSingleResultFlow(
            suspendFunc = { scheduleService.getScheduleDetail(id) },
            transform = { dto ->
                dto.toScheduleModel()
            }
        )
    }

    override fun getAllSchedules(): Flow<PagingData<ScheduleModel>> {
        return createPagingFlow(
            apiCall = {
                scheduleService.getAllSchedules(it)
            },
            transform = { dto ->
                dto.toScheduleModel()
            }
        )
    }
}