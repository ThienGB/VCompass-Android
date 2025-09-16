package com.vcompass.domain.model.rating

import com.vcompass.domain.model.response.BaseEntity

data class Rating(
    val idUser: String?,
    val rate: Int?,
    val content: String?,
): BaseEntity()