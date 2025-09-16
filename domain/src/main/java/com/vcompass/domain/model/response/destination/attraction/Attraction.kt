package com.vcompass.domain.model.response.destination.attraction

import com.vcompass.domain.model.response.destination.Destination

data class Attraction(
    val attractionName: String?,
    val price: Int?,
    val operatingHours: List<OperatingHour>?,
): Destination()

