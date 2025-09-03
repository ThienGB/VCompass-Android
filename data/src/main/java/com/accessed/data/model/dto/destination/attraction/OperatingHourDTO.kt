package com.accessed.data.model.dto.destination.attraction

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.destination.attraction.OperatingHour
import com.google.gson.annotations.SerializedName

data class OperatingHourDTO(
    @SerializedName("startDay")
    val startDay: String?,
    @SerializedName("endDay")
    val endDay: String?,
    @SerializedName("openTime")
    val openTime: String?,
    @SerializedName("closeTime")
    val closeTime: String?
) : MapperDTO<OperatingHour> {
    override fun toDomain(): OperatingHour {
        return OperatingHour(
            startDay = startDay,
            endDay = endDay,
            openTime = openTime,
            closeTime = closeTime
        )
    }
}