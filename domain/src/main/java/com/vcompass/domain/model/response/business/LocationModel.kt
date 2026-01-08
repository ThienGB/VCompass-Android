package com.vcompass.domain.model.response.business

data class LocationModel(
    val type: String?,
    val coordinates: List<Double>?,
    val address: String?,
    val city: String?,
    val postalCode: String?
)