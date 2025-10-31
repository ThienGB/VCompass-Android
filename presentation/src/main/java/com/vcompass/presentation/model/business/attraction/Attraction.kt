package com.vcompass.presentation.model.business.attraction

import com.vcompass.domain.model.response.business.BusinessModel

data class Attraction(
    val price: Int? = null,
    val operatingHours: List<OperatingHour>? = null,
): BusinessModel()

