package com.vcompass.data.model.dto.destination.accommodation

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.domain.model.response.destination.accommodation.AccommodationBed
import com.google.gson.annotations.SerializedName

data class AccommodationBedDTO(
    @SerializedName("nameBed")
    val nameBed: String?,
    @SerializedName("number")
    val number: Int?
) : MapperDTO<AccommodationBed> {
    override fun toDomain(): AccommodationBed {
        return AccommodationBed(
            nameBed = nameBed,
            number = number
        )
    }
}