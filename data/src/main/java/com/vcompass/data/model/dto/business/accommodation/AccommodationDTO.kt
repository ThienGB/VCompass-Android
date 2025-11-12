package com.vcompass.data.model.dto.business.accommodation

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.business.BusinessDTO
import com.vcompass.domain.util.tryParseObject
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