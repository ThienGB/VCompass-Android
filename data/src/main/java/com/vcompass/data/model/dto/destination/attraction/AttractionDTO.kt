package com.vcompass.data.model.dto.destination.attraction

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.destination.BusinessDTO
import com.vcompass.data.util.tryParseObject
import com.vcompass.domain.model.response.business.attraction.AttractionModel

data class AttractionDTO(
    @SerializedName("price")
    val price: Int?,
    @SerializedName("operatingHours")
    val operatingHours: List<OperatingHourDTO>?,
) : BusinessDTO()

fun AttractionDTO.toAttractionModel(): AttractionModel {
    return tryParseObject<AttractionModel>() ?: AttractionModel()
}
