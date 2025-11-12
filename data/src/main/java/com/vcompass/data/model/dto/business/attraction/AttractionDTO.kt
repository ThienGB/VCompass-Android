package com.vcompass.data.model.dto.business.attraction

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.business.BusinessDTO
import com.vcompass.domain.util.tryParseObject
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
