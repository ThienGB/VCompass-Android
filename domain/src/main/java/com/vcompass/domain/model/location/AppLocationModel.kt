package com.vcompass.domain.model.location

data class AppLocationModel(
    val latitude: Double,
    val longitude: Double,
    val city: String?,
    val shortAddress: String?,
    val fullAddress: String?
)