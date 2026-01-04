package com.vcompass.presentation.model.schedule

import com.vcompass.presentation.model.Base
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    val type: String?,
    val city: String?,
    val coordinates: List<Double>?,
) : Base()

