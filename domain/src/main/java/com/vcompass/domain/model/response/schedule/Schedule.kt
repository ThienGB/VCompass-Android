package com.vcompass.domain.model.response.schedule

import com.vcompass.domain.model.response.BaseEntity
import com.vcompass.domain.model.response.user.User

data class Schedule(
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
    val likes: List<ScheduleLike>?,
    val isPublic: Boolean?,
    val idInvitee: List<String>?,
    val tags: List<String>?,
    val videoSrc: String?
) : BaseEntity()