package com.vcompass.data.model.dto.business.attraction

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
)