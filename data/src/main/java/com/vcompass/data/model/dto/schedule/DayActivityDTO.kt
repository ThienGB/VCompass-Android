package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class DayActivityDTO(
    @SerializedName("day")
    val day: Int?,
    @SerializedName("activities")
    val activities: List<ActivityDTO>?,
    @SerializedName("name")
    val name: String?
) : BaseDTO()


