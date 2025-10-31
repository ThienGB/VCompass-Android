package com.vcompass.domain.model.response.business

import com.vcompass.domain.model.response.BaseModel

open class BusinessModel(
    val partnerId: String? = null,
    var name: String? = null,
    var description: String? = null,
    var location: LocationModel? = null,
    var images: List<String>? = null,
    var amenities: List<String>? = null,
    var contact: ContactInformationModel? = null,
    var registerDate: String? = null,
    var status: String? = null,
    val averageRating: Float? = null,
    val totalRatings: Float? = null,
): BaseModel()
