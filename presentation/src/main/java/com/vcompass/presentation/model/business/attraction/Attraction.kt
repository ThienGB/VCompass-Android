package com.vcompass.presentation.model.business.attraction

import com.vcompass.presentation.model.business.Business
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attraction(
    val price: Int? = null,
    val operatingHours: List<OperatingHour>? = null,
): Business()
