package com.vcompass.data.model.dto.destination

import com.google.gson.annotations.SerializedName

data class LocationDTO(
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("city")
    val city: String?
)