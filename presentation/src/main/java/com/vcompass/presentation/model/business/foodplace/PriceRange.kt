package com.vcompass.presentation.model.business.foodplace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriceRange(
    val maxPrice: Int?,
    val minPrice: Int?
) : Parcelable