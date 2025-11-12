package com.vcompass.data.model.dto.business.accommodation

import com.google.gson.annotations.SerializedName

data class RoomTypeDTO(
    @SerializedName("nameRoomType")
    val nameRoomType: String?,
    @SerializedName("numBed")
    val numBed: List<AccommodationBedDTO>?,
    @SerializedName("numPeople")
    val numPeople: AccommodationPeopleDTO?,
    @SerializedName("pricePerNight")
    val pricePerNight: Int?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("amenities")
    val amenities: List<String>?,
    @SerializedName("roomSize")
    val roomSize: Int?
)
