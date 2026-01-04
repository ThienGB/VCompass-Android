package com.vcompass.domain.usecase.schedule

import androidx.paging.PagingData
import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetAllSchedulesUseCase(private val repo: ScheduleRepository) {
    operator fun invoke(): Flow<PagingData<ScheduleModel>> {
        return repo.getAllSchedules()
    }
}