package com.accessed.data.model.dto.schedule

import com.accessed.data.model.dto.BaseDTO
import com.accessed.data.model.dto.MapperDTO
import com.accessed.data.model.dto.user.BaseUserDTO
import com.accessed.data.model.dto.withBase
import com.accessed.domain.model.response.schedule.Schedule
import com.google.gson.annotations.SerializedName

data class ScheduleDTO(
    @SerializedName("idUser")
    val user: BaseUserDTO?,
    @SerializedName("scheduleName")
    val scheduleName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("imgSrc")
    val imgSrc: List<String>?,
    @SerializedName("numDays")
    val numDays: Int,
    @SerializedName("dateStart")
    val dateStart: String?,
    @SerializedName("dateEnd")
    val dateEnd: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("activities")
    val activities: List<DayActivityDTO>?,
    @SerializedName("additionalExpenses")
    val additionalExpenses: List<AdditionalExpenseDTO>?,
    @SerializedName("comments")
    val comments: List<CommentDTO>?,
    @SerializedName("likes")
    val likes: List<ScheduleLikeDTO>?,
    @SerializedName("isPublic")
    val isPublic: Boolean?,
    @SerializedName("idInvitee")
    val idInvitee: List<String>?,
    @SerializedName("tags")
    val tags: List<String>?,
    @SerializedName("videoSrc")
    val videoSrc: String?
) : BaseDTO(), MapperDTO<Schedule> {
    override fun toDomain(): Schedule {
        return Schedule(
            idUser = this.user?.toDomain(),
            scheduleName = this.scheduleName,
            description = this.description,
            address = this.address,
            imgSrc = this.imgSrc,
            numDays = this.numDays,
            dateStart = this.dateStart,
            dateEnd = this.dateEnd,
            status = this.status,
            activities = this.activities?.map { it.toDomain() },
            additionalExpenses = this.additionalExpenses?.map { it.toDomain() },
            comments = this.comments?.map { it.toDomain() },
            likes = this.likes?.map { it.toDomain() },
            isPublic = this.isPublic,
            idInvitee = this.idInvitee,
            tags = this.tags,
            videoSrc = this.videoSrc,
        ).withBase(this)
    }
}

