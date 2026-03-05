package com.vcompass.presentation.model.business

import android.os.Parcelable
import com.vcompass.presentation.model.Base
import com.vcompass.presentation.model.business.attraction.OperatingHour
import kotlinx.parcelize.Parcelize

@Parcelize
open class Business(
    val partnerId: String? = null,
    var name: String? = null,
    var description: String? = null,
    var location: Location? = null,
    var images: List<String>? = null,
    var amenities: List<String>? = null,
    var contact: ContactInformation? = null,
    var registerDate: String? = null,
    var status: String? = null,
    val averageRating: Float? = null,
    val totalRatings: Float? = null,
    val operatingHours: List<OperatingHour>? = null,
) : Base(), Parcelable

fun Business.getLongitude(): Double {
    return location?.coordinates?.get(0) ?: 0.0
}

fun Business.getLatitude(): Double {
    return location?.coordinates?.get(1) ?: 0.0
}

fun Business.getFirstImage(): String {
    return images.orEmpty().firstOrNull().orEmpty()
}
