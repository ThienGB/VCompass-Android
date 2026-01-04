package com.vcompass.domain.model.response.schedule

import com.vcompass.domain.model.response.BaseModel

data class DestinationModel(
    val type: String?,
    val city: String?,
    val coordinates: List<Double>?,
) : BaseModel()

