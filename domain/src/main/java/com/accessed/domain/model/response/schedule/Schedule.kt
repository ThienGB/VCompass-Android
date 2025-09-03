package com.accessed.domain.model.response.schedule

import com.accessed.domain.model.response.BaseEntity
import com.accessed.domain.model.response.user.User

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