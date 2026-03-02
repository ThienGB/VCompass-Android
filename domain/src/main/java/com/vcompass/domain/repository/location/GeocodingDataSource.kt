package com.vcompass.domain.repository.location

import android.location.Location

interface GeocodingDataSource {
    suspend fun getShortAddress(
        latitude: Double,
        longitude: Double
    ): String?

    suspend fun getFullAddress(
        latitude: Double,
        longitude: Double
    ): String?

    suspend fun getCurrentLocation(): Location?
}