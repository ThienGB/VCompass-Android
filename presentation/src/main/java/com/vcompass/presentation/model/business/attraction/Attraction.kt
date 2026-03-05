package com.vcompass.presentation.model.business.attraction

import com.vcompass.domain.model.response.business.attraction.AttractionModel
import com.vcompass.presentation.model.business.Business
import com.vcompass.presentation.util.tryParseObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attraction(
    val price: Int? = null
): Business()

fun AttractionModel.toAttraction(): Attraction {
    return tryParseObject<Attraction>() ?: Attraction()
}