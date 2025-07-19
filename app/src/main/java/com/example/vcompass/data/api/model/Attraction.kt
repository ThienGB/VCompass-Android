package com.example.vcompass.data.api.model

data class Attraction(
    val _id: String?,
    val attractionName: String?,
    val description: String?,
    val location: Location?,
    val city: String?,
    val price: Int?,
    val images: List<String>?,
    val amenities: List<String>?,
    val operatingHours: List<OperatingHour>?,
    val ratings: List<Rating>?,
    val createdAt: String?
)

data class OperatingHour(
    val startDay: String?,
    val endDay: String?,
    val openTime: String?,
    val closeTime: String?
)
