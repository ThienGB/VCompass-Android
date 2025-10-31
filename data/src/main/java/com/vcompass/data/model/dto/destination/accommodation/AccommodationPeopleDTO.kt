package com.vcompass.data.model.dto.destination.accommodation

import com.google.gson.annotations.SerializedName

data class AccommodationPeopleDTO(
    @SerializedName("adult")
    val adult: Int?,
    @SerializedName("child")
    val child: Int?
)