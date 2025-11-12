package com.vcompass.presentation.model.business.accommodation

import com.vcompass.presentation.model.business.Business
import kotlinx.parcelize.Parcelize

@Parcelize
data class Accommodation(
    val price: Int? = null,
    val note: String? = null,
): Business()

