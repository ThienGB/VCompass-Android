package com.vcompass.data.model.dto.destination.accommodation

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.data.model.dto.destination.DestinationDTO
import com.vcompass.data.model.dto.destination.withDestination
import com.vcompass.domain.model.response.destination.accommodation.Accommodation
import com.google.gson.annotations.SerializedName

data class AccommodationDTO(
    @SerializedName("price")
    val price: Int?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("roomTypes")
    val roomTypes: List<RoomTypeDTO>?,
) : DestinationDTO(), MapperDTO<Accommodation> {
    override fun toDomain(): Accommodation {
        return Accommodation(
            price = price,
            note = note,
            roomTypes = roomTypes?.map { it.toDomain() },
        ).withDestination(this)
    }
}

