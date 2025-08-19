package com.accessed.data.model.dto.schedule

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.login.LoginModel
import com.accessed.domain.model.response.schedule.Schedule
import com.google.gson.annotations.SerializedName

data class ScheduleDTO(
    @SerializedName("_id") val id: String?,
    val idUser: User?,
    val scheduleName: String?,
    val description: String?,
    val address: String?,
    val imgSrc: List<String>?,
    val numDays: Int,
    val dateStart: String?,
    val dateEnd: String?,
    val status: String?,
    val activities: List<DayActivity>?,
    val additionalExpenses: List<AdditionalExpense>?,
    val comments: List<Comment>?,
    val likes: List<Like>?,
    val createdAt: String?,
    val isPublic: Boolean?,
    val idInvitee: List<String>?,
    val tags: List<String>?,
    val videoSrc: String?
): MapperDTO<Schedule>{
    override fun toDomain(): Schedule {
        return Schedule()
    }
}

data class DayActivity(
    val day: Int?,
    val activity: List<ActivityItem>?
)

data class ActivityItem(
    val activityType: String,
    val idDestination: String?,
    val name: String?,
    val address: String?,
    val imgSrc: List<String>?,
    val cost: Int?,
    val costDescription: String?,
    val description: String?,
    val timeStart: String?,
    val timeEnd: String?,
    @SerializedName("_id") val id: String?,
    val destination: Any? // Có thể là Accommodation, Attraction hoặc FoodService
)


data class Comment(
    val idUser: String?,
    val userName: String?,
    val avatar: String?,
    val content: String?,
    val createdAt: String?,
    val replies: List<Reply>?,
    @SerializedName("_id") val id: String?
)

data class Reply(
    val idUser: String?,
    val userName: String?,
    val avatar: String?,
    val content: String?,
    val createdAt: String?,
    @SerializedName("_id") val id: String?
)

data class Like(
    val idUser: String?,
    val createdAt: String?,
    @SerializedName("_id") val id: String?
)

data class User(
    val _id: String,
    val name: String?,
    val email: String?,
    val avatar: String? = null // nếu có
)
data class AdditionalExpense(
    val cost: Int?,
    val description: String?
)

