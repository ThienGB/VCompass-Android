package com.vcompass.presentation.model.business.foodplace

import com.vcompass.presentation.model.business.Business
import com.vcompass.presentation.model.business.attraction.OperatingHour
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodPlace(
    val serviceType: String? = null,
    val price: PriceRange? = null,
    val menuImages: List<String>? = null,
    val operatingHours: List<OperatingHour>? = null
) : Business()

