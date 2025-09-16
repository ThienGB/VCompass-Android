package com.vcompass.data.model.dto.destination

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.domain.model.response.destination.Location
import com.google.gson.annotations.SerializedName

data class LocationDTO(
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("address")
    val address: String?
) : MapperDTO<Location> {
    override fun toDomain(): Location {
        return Location(
            latitude = latitude,
            longitude = longitude,
            address = address
        )
    }
}