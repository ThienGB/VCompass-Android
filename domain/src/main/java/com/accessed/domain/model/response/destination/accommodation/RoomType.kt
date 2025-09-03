package com.accessed.domain.model.response.destination.accommodation

data class RoomType(
    val nameRoomType: String?,
    val numBed: List<AccommodationBed>?,
    val numPeople: AccommodationPeople?,
    val pricePerNight: Int?,
    val images: List<String>?,
    val status: String?,
    val description: String?,
    val amenities: List<String>?,
    val roomSize: Int?
)
