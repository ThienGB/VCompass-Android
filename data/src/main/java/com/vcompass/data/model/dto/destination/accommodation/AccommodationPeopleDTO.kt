package com.vcompass.data.model.dto.destination.accommodation

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.domain.model.response.destination.accommodation.AccommodationPeople
import com.google.gson.annotations.SerializedName

data class AccommodationPeopleDTO(
    @SerializedName("adult")
    val adult: Int?,
    @SerializedName("child")
    val child: Int?
) : MapperDTO<AccommodationPeople> {
    override fun toDomain(): AccommodationPeople {
        return AccommodationPeople(
            adult = adult,
            child = child
        )
    }
}