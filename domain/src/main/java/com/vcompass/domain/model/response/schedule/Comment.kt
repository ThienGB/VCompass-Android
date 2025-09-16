package com.vcompass.domain.model.response.schedule

import com.vcompass.domain.model.response.BaseEntity

data class Comment(
    val idUser: String?,
    val userName: String?,
    val avatar: String?,
    val content: String?,
    val replies: List<Comment>?,
) : BaseEntity()
