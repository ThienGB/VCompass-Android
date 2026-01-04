package com.vcompass.data.model.dto.schedule

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.user.UserDTO
import com.vcompass.domain.util.tryParseObject
import com.vcompass.domain.model.response.schedule.ScheduleModel

data class ScheduleDTO(
    @SerializedName("userId")
    val user: UserDTO?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("departure")
    val departure: String?,
    @SerializedName("destinations")
    val destinations: List<DestinationDTO>?,
    @SerializedName("numDays")
    val numDays: Int,
    @SerializedName("dateStart")
    val dateStart: String?,
    @SerializedName("dateEnd")
    val dateEnd: String?,
    @SerializedName("video")
    val video: String?,
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("types")
    val types: List<String>?,
    @SerializedName("isPublic")
    val isPublic: Boolean?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tags")
    val tags: List<String>?,
    @SerializedName("idInvited")
    val idInvited: List<String>?,
    @SerializedName("days")
    val days: List<DayActivityDTO>?,
    @SerializedName("additionalExpenses")
    val additionalExpenses: List<AdditionalExpenseDTO>?,
    @SerializedName("hasFavorite")
    val hasFavorite: Boolean? = null,
    @SerializedName("hasLiked")
    val hasLiked: Boolean? = null,
    @SerializedName("commentsCount")
    val commentsCount: Int? = null,
    @SerializedName("likesCount")
    val likesCount: Int? = null,
    @SerializedName("totalCost")
    val totalCost: Int? = null,
    @SerializedName("sharesCount")
    val sharesCount: Int? = null,
) : BaseDTO()

fun ScheduleDTO.toScheduleModel() : ScheduleModel{
    return tryParseObject<ScheduleModel>() ?: ScheduleModel()
}

