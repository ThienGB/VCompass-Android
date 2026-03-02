package com.vcompass.domain.usecase.location

import com.vcompass.domain.model.location.AppLocationModel
import com.vcompass.domain.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow

class ObserveSelectedLocationUseCase(
    private val repo: LocationRepository
) {
    operator fun invoke(): Flow<AppLocationModel?> = repo.selectedLocation
}
