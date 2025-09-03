package com.accessed.data.model.dto.destination.accommodation

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.destination.accommodation.AccommodationPeople
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