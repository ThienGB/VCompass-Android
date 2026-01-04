package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class DestinationDTO(
    @SerializedName("type")
    val type: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("coordinates")
    val coordinates: List<Double>?,
) : BaseDTO()

