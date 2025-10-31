package com.vcompass.domain.model.response.business.attraction

import com.vcompass.domain.model.response.business.BusinessModel

data class AttractionModel(
    val price: Int? = null,
    val operatingHours: List<OperatingHourModel>? = null,
): BusinessModel()

