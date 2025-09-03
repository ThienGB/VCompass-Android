package com.accessed.domain.model.response.schedule

data class DayActivity(
    val day: Int?,
    val activity: List<ScheduleActivity>?
)
