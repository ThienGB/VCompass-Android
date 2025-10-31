package com.vcompass.domain.model.rating

import com.vcompass.domain.model.response.BaseModel

data class Rating(
    val idUser: String?,
    val rate: Int?,
    val content: String?,
): BaseModel()