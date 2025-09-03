package com.accessed.data.model.dto.schedule

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.schedule.DayActivity
import com.google.gson.annotations.SerializedName

data class DayActivityDTO(
    @SerializedName("day")
    val day: Int?,
    @SerializedName("activity")
    val activity: List<ScheduleActivityDTO>?
): MapperDTO<DayActivity> {
    override fun toDomain(): DayActivity {
        return DayActivity(
            day = this.day,
            activity = this.activity?.map { it.toDomain() }
        )
    }
}


