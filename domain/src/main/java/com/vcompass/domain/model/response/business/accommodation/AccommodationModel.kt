package com.vcompass.domain.model.response.business.accommodation

import com.vcompass.domain.model.response.business.BusinessModel

data class AccommodationModel(
    val price: Int? = null,
    val note: String? = null,
): BusinessModel()

