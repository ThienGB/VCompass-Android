package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import com.vcompass.domain.model.response.schedule.ScheduleModel
import com.vcompass.domain.model.response.user.UserModel
import com.vcompass.presentation.model.Base
import com.vcompass.presentation.util.tryParseObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val user: UserModel? = null,
    val name: String? = null,
    val description: String? = null,
    val departure: String? = null,
    val destinations: List<String>? = null,
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
    val comments: List<Comment>? = null,
    val likes: List<Like>? = null
) : Base(), Parcelable

fun ScheduleModel.toSchedule(): Schedule{
    return tryParseObject<Schedule>() ?: Schedule()
}