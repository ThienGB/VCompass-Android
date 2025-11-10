package com.vcompass.data.model.dto.destination.foodservice

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.destination.BusinessDTO
import com.vcompass.data.model.dto.destination.attraction.OperatingHourDTO
import com.vcompass.domain.util.tryParseObject
import com.vcompass.domain.model.response.business.foodservice.FoodPlaceModel

data class FoodPlaceDTO(
    @SerializedName("serviceType")
    val serviceType: String?,
    @SerializedName("price")
    val price: PriceRangeDTO?,
    @SerializedName("menuImages")
    val menuImages: List<String>?,
    @SerializedName("operatingHours")
    val operatingHours: List<OperatingHourDTO>?
) : BusinessDTO()

fun FoodPlaceDTO.toAttractionModel(): FoodPlaceModel {
    return tryParseObject<FoodPlaceModel>() ?: FoodPlaceModel()
}

