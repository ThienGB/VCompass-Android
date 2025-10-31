package com.vcompass.presentation.model.schedule

import com.vcompass.domain.model.response.BaseModel

data class Comment(
    val idUser: String?,
    val content: String?,
    val replies: List<Comment>?,
) : BaseModel()
