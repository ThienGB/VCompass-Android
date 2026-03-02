package com.vcompass.domain.usecase.location

import com.vcompass.domain.model.location.AppLocationModel
import com.vcompass.domain.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationFromLatLngUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Flow<Result<AppLocationModel>> = repository.getLocationFromLatLng(latitude, longitude)
}
