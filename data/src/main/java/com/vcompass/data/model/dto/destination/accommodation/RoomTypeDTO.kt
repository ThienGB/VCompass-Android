package com.vcompass.data.model.dto.destination.accommodation

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.domain.model.response.destination.accommodation.RoomType
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
) : MapperDTO<RoomType> {
    override fun toDomain(): RoomType {
        return RoomType(
            nameRoomType = nameRoomType,
            numBed = numBed?.map { it.toDomain() },
            numPeople = numPeople?.toDomain(),
            pricePerNight = pricePerNight,
            images = images,
            status = status,
            description = description,
            amenities = amenities,
            roomSize = roomSize
        )
    }
}
