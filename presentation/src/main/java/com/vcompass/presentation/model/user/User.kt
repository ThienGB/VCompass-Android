package com.vcompass.presentation.model.user

import com.vcompass.domain.model.response.BaseModel

data class User(
    val name: String?,
    val email: String?,
    val avatar: String?
) : BaseModel()