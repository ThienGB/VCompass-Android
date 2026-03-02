package com.vcompass.domain.model.location

data class AppLocationModel(
    val latitude: Double,
    val longitude: Double,
    val shortAddress: String?,
    val fullAddress: String?
)