package com.vcompass.domain.model.response.schedule

data class DayActivityModel(
    val day: Int?,
    val activities: List<ActivityModel>?,
    val name: String?
)
