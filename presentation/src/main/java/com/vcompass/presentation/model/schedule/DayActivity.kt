package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DayActivity(
    val day: Int? = null,
    val activity: List<Activity>? = null
) : Parcelable
