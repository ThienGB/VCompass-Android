package com.vcompass.domain.model.response.schedule

import com.vcompass.domain.model.response.BaseModel
import com.vcompass.domain.model.response.user.UserModel

data class ScheduleModel(
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
    val days: List<DayActivityModel>? = null,
    val additionalExpenses: List<AdditionalExpenseModel>? = null,
    val comments: List<CommentModel>? = null,
    val likes: List<LikeModel>? = null
) : BaseModel()