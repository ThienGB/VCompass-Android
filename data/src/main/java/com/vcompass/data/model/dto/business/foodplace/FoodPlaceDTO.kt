package com.vcompass.data.model.dto.business.foodplace

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.business.BusinessDTO
import com.vcompass.domain.util.tryParseObject
import com.vcompass.domain.model.response.business.foodplace.FoodPlaceModel

data class FoodPlaceDTO(
    @SerializedName("serviceType")
    val serviceType: String?,
    @SerializedName("price")
    val price: PriceRangeDTO?,
    @SerializedName("menuImages")
    val menuImages: List<String>?
) : BusinessDTO()

fun FoodPlaceDTO.toFoodPlaceModel(): FoodPlaceModel {
    return tryParseObject<FoodPlaceModel>() ?: FoodPlaceModel()
}

