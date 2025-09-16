package com.vcompass.domain.model.response.destination.foodservice

import com.vcompass.domain.model.response.destination.Destination
import com.vcompass.domain.model.response.destination.attraction.OperatingHour

data class FoodService(
    val idPartner: String?,
    val serviceType: String?,
    val foodServiceName: String?,
    val price: PriceRange?,
    val menuImages: List<String>?,
    val contactNumber: String?,
    val email: String?,
    val operatingHours: List<OperatingHour>?
) : Destination()

