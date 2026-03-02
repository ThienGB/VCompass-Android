package com.vcompass.domain.repository.location

import com.vcompass.domain.model.location.AppLocationModel
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    val selectedLocation: Flow<AppLocationModel?>

    suspend fun setCurrentLocation(
        latitude: Double,
        longitude: Double
    ): Flow<Result<Unit>>

    suspend fun setSearchLocation(
        location: AppLocationModel
    ): Flow<Result<Unit>>
    suspend fun getCurrentLocation(): Flow<Result<AppLocationModel>>
    suspend fun getLocationFromLatLng(
        latitude: Double,
        longitude: Double
    ): Flow<Result<AppLocationModel>>
}
