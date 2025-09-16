package com.vcompass.data.model.dto.destination.foodservice

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.domain.model.response.destination.foodservice.PriceRange
import com.google.gson.annotations.SerializedName

data class PriceRangeDTO(
    @SerializedName("maxPrice")
    val maxPrice: Int?,
    @SerializedName("minPrice")
    val minPrice: Int?
) : MapperDTO<PriceRange> {
    override fun toDomain(): PriceRange {
        return PriceRange(
            maxPrice = maxPrice,
            minPrice = minPrice
        )
    }
}