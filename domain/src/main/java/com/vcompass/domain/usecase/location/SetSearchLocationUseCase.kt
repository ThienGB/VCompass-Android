package com.vcompass.domain.usecase.location

import com.vcompass.domain.model.location.AppLocationModel
import com.vcompass.domain.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow

class SetSearchLocationUseCase(
    private val repo: LocationRepository
) {
    suspend operator fun invoke(location: AppLocationModel): Flow<Result<Unit>> {
        return repo.setSearchLocation(location)
    }
}
