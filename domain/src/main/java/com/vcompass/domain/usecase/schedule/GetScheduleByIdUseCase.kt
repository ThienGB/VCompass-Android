package com.vcompass.domain.usecase.schedule

import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.repository.schedule.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetScheduleByIdUseCase(private val repo: ScheduleRepository) {
    suspend operator fun invoke(id: String): Flow<Result<ScheduleModel>> {
        return repo.getScheduleById(id)
    }
}