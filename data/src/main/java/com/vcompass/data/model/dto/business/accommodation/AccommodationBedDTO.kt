package com.vcompass.data.model.dto.business.accommodation

import com.google.gson.annotations.SerializedName

data class AccommodationBedDTO(
    @SerializedName("nameBed")
    val nameBed: String?,
    @SerializedName("number")
    val number: Int?
)