package com.vcompass.presentation.model.business.attraction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OperatingHour(
    val startDay: String?,
    val endDay: String?,
    val openTime: String?,
    val closeTime: String?
) : Parcelable