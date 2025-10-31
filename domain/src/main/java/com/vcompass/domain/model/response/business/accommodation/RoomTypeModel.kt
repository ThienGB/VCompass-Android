package com.vcompass.domain.model.response.business.accommodation

data class RoomTypeModel(
    val nameRoomType: String?,
    val numBed: List<AccommodationBedModel>?,
    val numPeople: AccommodationPeopleModel?,
    val pricePerNight: Int?,
    val images: List<String>?,
    val status: String?,
    val description: String?,
    val amenities: List<String>?,
    val roomSize: Int?
)
