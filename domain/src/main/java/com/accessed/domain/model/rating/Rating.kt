package com.accessed.domain.model.rating

import com.accessed.domain.model.response.BaseEntity

data class Rating(
    val idUser: String?,
    val rate: Int?,
    val content: String?,
): BaseEntity()