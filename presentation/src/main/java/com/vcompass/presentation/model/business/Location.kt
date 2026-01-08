package com.vcompass.presentation.model.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val type: String? = null,
    val coordinates: List<Double>? = null,
    val address: String? = null,
    val city: String? = null,
    val postalCode: String? = null
) : Parcelable