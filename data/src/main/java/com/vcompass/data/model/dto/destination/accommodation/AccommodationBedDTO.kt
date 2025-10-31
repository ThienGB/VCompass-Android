package com.vcompass.data.model.dto.destination.accommodation

import com.google.gson.annotations.SerializedName

data class AccommodationBedDTO(
    @SerializedName("nameBed")
    val nameBed: String?,
    @SerializedName("number")
    val number: Int?
)