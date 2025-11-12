package com.vcompass.presentation.model.business.accommodation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccommodationPeople(
    val adult: Int?,
    val child: Int?
) : Parcelable