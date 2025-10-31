package com.vcompass.domain.model.response.schedule

import com.vcompass.domain.model.response.BaseModel

data class CommentModel(
    val idUser: String?,
    val content: String?,
    val replies: List<CommentModel>?,
) : BaseModel()
