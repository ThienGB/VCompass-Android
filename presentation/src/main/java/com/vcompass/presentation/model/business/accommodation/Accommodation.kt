package com.vcompass.presentation.model.business.accommodation

import com.vcompass.domain.model.response.business.accommodation.AccommodationModel
import com.vcompass.presentation.model.business.Business
import com.vcompass.presentation.util.tryParseObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Accommodation(
    val price: Int? = null,
    val note: String? = null,
): Business()

fun AccommodationModel.toAccommodation(): Accommodation {
    return tryParseObject<Accommodation>() ?: Accommodation()
}

