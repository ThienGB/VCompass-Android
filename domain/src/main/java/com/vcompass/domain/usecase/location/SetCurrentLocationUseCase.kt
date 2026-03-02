package com.vcompass.domain.usecase.location

import com.vcompass.domain.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow

class SetCurrentLocationUseCase(
    private val repo: LocationRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Flow<Result<Unit>> {
        return repo.setCurrentLocation(latitude, longitude)
    }
}
