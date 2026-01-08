package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import com.vcompass.presentation.model.Base
import com.vcompass.presentation.model.business.Business
import kotlinx.parcelize.Parcelize

@Parcelize
data class Activity(
    val itemType: String = "",
    val activityType: String = "",
    val idDestination: String = "",
    val name: String = "",
    val address: String = "",
    val images: List<String> = emptyList(),
    val cost: Int = 0,
    val costDescription: String = "",
    val description: String = "",
    val timeStart: String = "",
    val timeEnd: String = "",
    val business: Business = Business()
) : Base(), Parcelable
