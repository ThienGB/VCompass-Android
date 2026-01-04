package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.model.response.user.UserModel
import com.vcompass.presentation.model.Base
import com.vcompass.presentation.model.user.User
import com.vcompass.presentation.util.tryParseObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    @SerializedName("userId")
    val user: User? = null,
    val name: String? = null,
    val description: String? = null,
    val departure: String? = null,
    val destinations: List<Destination>? = null,
    val numDays: Int? = null,
    val dateStart: String? = null,
    val dateEnd: String? = null,
    val video: String? = null,
    val images: List<String>? = null,
    val types: List<String>? = null,
    val isPublic: Boolean? = null,
    val status: String? = null,
    val tags: List<String>? = null,
    val idInvited: List<String>? = null,
    val days: List<DayActivity>? = null,
    val additionalExpenses: List<AdditionalExpense>? = null,
    val commentsCount: Int? = null,
    val likesCount: Int? = null,
    val totalCost: Int? = null,
    val sharesCount: Int? = null,
    val hasFavorite: Boolean? = null,
    val hasLiked: Boolean? = null,
) : Base(), Parcelable

fun ScheduleModel.toSchedule(): Schedule {
    return tryParseObject<Schedule>() ?: Schedule()
}