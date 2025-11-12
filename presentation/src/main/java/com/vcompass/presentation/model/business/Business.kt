package com.vcompass.presentation.model.business

import android.os.Parcelable
import com.vcompass.presentation.model.Base
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
): Base(), Parcelable
