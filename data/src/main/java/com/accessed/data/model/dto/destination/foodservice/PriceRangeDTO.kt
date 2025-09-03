package com.accessed.data.model.dto.destination.foodservice

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.destination.foodservice.PriceRange
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