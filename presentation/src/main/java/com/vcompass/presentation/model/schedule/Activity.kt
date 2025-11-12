package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import com.vcompass.presentation.model.Base
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
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
    val destination: @RawValue Any?
) : Base(), Parcelable
