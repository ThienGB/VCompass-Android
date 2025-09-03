package com.accessed.domain.model.response.destination.attraction

import com.accessed.domain.model.response.destination.Destination

data class Attraction(
    val attractionName: String?,
    val price: Int?,
    val operatingHours: List<OperatingHour>?,
): Destination()

