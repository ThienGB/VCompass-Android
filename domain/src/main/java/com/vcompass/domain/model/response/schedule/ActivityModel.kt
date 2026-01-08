package com.vcompass.domain.model.response.schedule

import com.vcompass.domain.model.response.BaseModel
import com.vcompass.domain.model.response.business.BusinessModel

data class ActivityModel(
    val itemType: String,
    val activityType: String,
    val idDestination: String?,
    val name: String?,
    val address: String?,
    val images: List<String>?,
    val cost: Int?,
    val costDescription: String?,
    val description: String?,
    val timeStart: String?,
    val timeEnd: String?,
    val business: BusinessModel? = null
) : BaseModel()
