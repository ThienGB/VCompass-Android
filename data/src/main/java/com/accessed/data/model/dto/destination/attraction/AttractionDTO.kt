package com.accessed.data.model.dto.destination.attraction

import com.accessed.data.model.dto.MapperDTO
import com.accessed.data.model.dto.destination.DestinationDTO
import com.accessed.data.model.dto.destination.withDestination
import com.accessed.domain.model.response.destination.attraction.Attraction
import com.google.gson.annotations.SerializedName

data class AttractionDTO(
    @SerializedName("attractionName")
    val attractionName: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("operatingHours")
    val operatingHours: List<OperatingHourDTO>?,
) : DestinationDTO(), MapperDTO<Attraction> {
    override fun toDomain(): Attraction {
        return Attraction(
            attractionName = attractionName,
            price = price,
            operatingHours = operatingHours?.map { it.toDomain() },
        ).withDestination(this)
    }

}

