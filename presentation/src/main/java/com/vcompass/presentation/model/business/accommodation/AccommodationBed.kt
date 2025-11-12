package com.vcompass.presentation.model.business.accommodation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccommodationBed(
    val nameBed: String?,
    val number: Int?
) : Parcelable