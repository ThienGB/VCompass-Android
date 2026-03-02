package com.vcompass.data.repository.location

import com.vcompass.domain.repository.location.GeocodingDataSource
import com.vcompass.data.util.asResultFlow
import com.vcompass.data.util.asUnitResultFlow
import com.vcompass.domain.model.location.AppLocationModel
import com.vcompass.domain.repository.location.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationRepositoryImpl(
    private val geocoder: GeocodingDataSource
) : LocationRepository {

    private val _selectedLocation =
        MutableStateFlow<AppLocationModel?>(null)

    override val selectedLocation =
        _selectedLocation.asStateFlow()

    override suspend fun setCurrentLocation(
        latitude: Double,
        longitude: Double
    ): Flow<Result<Unit>> {
        val short = geocoder.getShortAddress(latitude, longitude)
        val full = geocoder.getFullAddress(latitude, longitude)

        _selectedLocation.value = AppLocationModel(
            latitude = latitude,
            longitude = longitude,
            shortAddress = short,
            fullAddress = full
        )
        return asUnitResultFlow()
    }

    override suspend fun setSearchLocation(
        location: AppLocationModel
    ): Flow<Result<Unit>> {
        _selectedLocation.value = location
        return asUnitResultFlow()
    }

    override suspend fun getCurrentLocation(): Flow<Result<AppLocationModel>> {
        val location = geocoder.getCurrentLocation()

        val latitude = location?.latitude ?: DefaultLocation.LATITUDE
        val longitude = location?.longitude ?: DefaultLocation.LONGITUDE

        return AppLocationModel(
            latitude = latitude,
            longitude = longitude,
            shortAddress = geocoder.getShortAddress(latitude, longitude),
            fullAddress = geocoder.getFullAddress(latitude, longitude)
        ).asResultFlow()
    }

    override suspend fun getLocationFromLatLng(
        latitude: Double,
        longitude: Double
    ): Flow<Result<AppLocationModel>> =
        AppLocationModel(
            latitude = latitude,
            longitude = longitude,
            shortAddress = geocoder.getShortAddress(latitude, longitude),
            fullAddress = geocoder.getFullAddress(latitude, longitude)
        ).asResultFlow()
}

object DefaultLocation {
    const val LATITUDE = 10.776889
    const val LONGITUDE = 106.700806
}
