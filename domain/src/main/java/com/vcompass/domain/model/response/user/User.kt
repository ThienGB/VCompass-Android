package com.vcompass.domain.model.response.user

import com.vcompass.domain.model.response.BaseEntity

data class User(
    val name: String?,
    val email: String?,
    val avatar: String?
) : BaseEntity()