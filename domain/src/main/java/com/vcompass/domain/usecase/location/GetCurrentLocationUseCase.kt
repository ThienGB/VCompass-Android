package com.vcompass.domain.usecase.location

import com.vcompass.domain.model.location.AppLocationModel
import com.vcompass.domain.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentLocationUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(): Flow<Result<AppLocationModel>> =
        repository.getCurrentLocation()
}
