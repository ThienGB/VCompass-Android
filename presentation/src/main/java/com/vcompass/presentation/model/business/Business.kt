package com.vcompass.presentation.model.business

import com.vcompass.domain.model.response.BaseModel

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
): BaseModel()
