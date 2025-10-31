package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class DayActivityDTO(
    @SerializedName("day")
    val day: Int?,
    @SerializedName("activity")
    val activity: List<ActivityDTO>?
) : BaseDTO()


