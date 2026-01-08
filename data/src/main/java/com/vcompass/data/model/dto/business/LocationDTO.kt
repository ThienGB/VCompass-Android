package com.vcompass.data.model.dto.business

import com.google.gson.annotations.SerializedName

data class LocationDTO(
    @SerializedName("type")
    val type: String?,
    @SerializedName("coordinates")
    val coordinates: List<Double>?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("postalCode")
    val postalCode: String?
)