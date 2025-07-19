package com.example.vcompass.data.api.model

data class FoodService(
    val _id: String?,
    val idPartner: String?,
    val serviceType: String?,
    val foodServiceName: String?,
    val description: String?,
    val location: Location?,
    val city: String?,
    val price: PriceRange?,
    val images: List<String>?,
    val menuImages: List<String>?,
    val registerDate: String?,
    val status: String?,
    val ratings: List<Rating>?,
    val amenities: List<String>?,
    val contactNumber: String?,
    val email: String?,
    val operatingHours: List<OperatingHour>?
)

data class PriceRange(
    val maxPrice: Int?,
    val minPrice: Int?
)
