package com.vcompass.domain.model.response.user

import com.vcompass.domain.model.response.BaseModel

data class UserModel(
    val name: String?,
    val email: String?,
    val avatar: String?
) : BaseModel()