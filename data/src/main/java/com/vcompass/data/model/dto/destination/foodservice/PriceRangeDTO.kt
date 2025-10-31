package com.vcompass.data.model.dto.destination.foodservice

import com.google.gson.annotations.SerializedName

data class PriceRangeDTO(
    @SerializedName("maxPrice")
    val maxPrice: Int?,
    @SerializedName("minPrice")
    val minPrice: Int?
)