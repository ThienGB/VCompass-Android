package com.vcompass.domain.repository.schedule

import androidx.paging.PagingData
import com.vcompass.domain.model.response.schedule.ScheduleModel
import kotlinx.coroutines.flow.Flow

interface   ScheduleRepository {
    suspend fun getScheduleDetail(id: String): Flow<Result<ScheduleModel>>
    fun getAllSchedules(): Flow<PagingData<ScheduleModel>>
}