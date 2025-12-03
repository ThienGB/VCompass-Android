package com.vcompass.domain.model.response.business.foodplace

import com.vcompass.domain.model.response.business.BusinessModel
import com.vcompass.domain.model.response.business.attraction.OperatingHourModel

data class FoodPlaceModel(
    val serviceType: String? = null,
    val price: PriceRangeModel? = null,
    val menuImages: List<String>? = null
) : BusinessModel()

