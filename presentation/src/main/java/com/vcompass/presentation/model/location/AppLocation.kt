package com.vcompass.presentation.model.location

import com.vcompass.domain.model.location.AppLocationModel
import com.vcompass.presentation.util.tryParseObject

data class AppLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val shortAddress: String? = null,
    val fullAddress: String? = null
)

fun AppLocationModel.toAppLocation() : AppLocation{
    return tryParseObject<AppLocation>() ?: AppLocation()
}