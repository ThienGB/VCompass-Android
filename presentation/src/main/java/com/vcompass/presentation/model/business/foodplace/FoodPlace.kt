package com.vcompass.presentation.model.business.foodplace

import com.vcompass.domain.model.response.business.BusinessModel
import com.vcompass.domain.model.response.business.attraction.OperatingHourModel

data class FoodPlace(
    val serviceType: String? = null,
    val price: PriceRange? = null,
    val menuImages: List<String>? = null,
    val operatingHours: List<OperatingHourModel>? = null
) : BusinessModel()

