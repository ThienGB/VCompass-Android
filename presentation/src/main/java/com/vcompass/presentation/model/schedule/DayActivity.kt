package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayActivity(
    val day: Int? = null,
    val name: String = "",
    val activities: List<Activity>? = null
) : Parcelable
