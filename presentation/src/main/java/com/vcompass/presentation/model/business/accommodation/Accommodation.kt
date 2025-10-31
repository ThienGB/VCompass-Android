package com.vcompass.presentation.model.business.accommodation

import com.vcompass.domain.model.response.business.BusinessModel

data class Accommodation(
    val price: Int? = null,
    val note: String? = null,
): BusinessModel()

