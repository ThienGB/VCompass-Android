package com.vcompass.presentation.model.schedule

import com.vcompass.domain.model.response.BaseModel

data class Activity(
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
    val destination: Any?
) : BaseModel()
