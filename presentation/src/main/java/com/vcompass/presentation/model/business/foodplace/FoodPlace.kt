package com.vcompass.presentation.model.business.foodplace

import com.vcompass.domain.model.response.business.foodplace.FoodPlaceModel
import com.vcompass.presentation.model.business.Business
import com.vcompass.presentation.util.formatThousandK
import com.vcompass.presentation.util.tryParseObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodPlace(
    val serviceType: String? = null,
    val price: PriceRange? = null,
    val menuImages: List<String>? = null
) : Business()

fun FoodPlaceModel.toFoodPlace(): FoodPlace {
    return tryParseObject<FoodPlace>() ?: FoodPlace()
}

fun FoodPlace.getPriceRange(): String {
    price?.let {
        return "${it.minPrice?.formatThousandK()} - ${it.maxPrice?.formatThousandK()}"
    }
    return ""
}

