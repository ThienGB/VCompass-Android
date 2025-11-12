package com.vcompass.presentation.model.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val latitude: Double?,
    val longitude: Double?,
    val address: String?
) : Parcelable