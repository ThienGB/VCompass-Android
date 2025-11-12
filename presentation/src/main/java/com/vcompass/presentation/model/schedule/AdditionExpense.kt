package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdditionalExpense(
    val cost: Int?,
    val description: String?
) : Parcelable