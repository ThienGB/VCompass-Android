package com.vcompass.data.model.dto.destination.accommodation

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.destination.BusinessDTO
import com.vcompass.data.util.tryParseObject
import com.vcompass.domain.model.response.business.accommodation.AccommodationModel

data class AccommodationDTO(
    @SerializedName("price")
    val price: Int?,
    @SerializedName("note")
    val note: String?
) : BusinessDTO()

fun AccommodationDTO.toAccommodationModel(): AccommodationModel {
    return tryParseObject<AccommodationModel>() ?: AccommodationModel()
}