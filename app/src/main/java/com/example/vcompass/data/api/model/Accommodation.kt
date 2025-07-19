package com.example.vcompass.data.api.model

data class Accommodation(
    val _id: String?,
    val name: String?,
    val description: String?,
    val location: Location?,
    val price: Int?,
    val city: String?,
    val images: List<String>?,
    val amenities: List<String>?,
    val contact: Contact?,
    val note: String?,
    val registerDate: String?,
    val status: String?,
    val roomTypes: List<RoomType>?,
    val ratings: List<Rating>?
)

data class RoomType(
    val nameRoomType: String?,
    val numBed: List<Bed>?,
    val numPeople: People?,
    val pricePerNight: Int?,
    val images: List<String>?,
    val status: String?,
    val description: String?,
    val amenities: List<String>?,
    val roomSize: Int?
)

data class Bed(
    val nameBed: String?,
    val number: Int?
)

data class People(
    val adult: Int?,
    val child: Int?
)

data class Contact(
    val phone: String?,
    val email: String?
)

data class Rating(
    val idUser: String?,
    val rate: Int?,
    val content: String?,
    val createdAt: String?
)

data class Location(
    val latitude: Double?,
    val longitude: Double?,
    val address: String?
)

