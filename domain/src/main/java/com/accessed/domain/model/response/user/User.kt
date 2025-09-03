package com.accessed.domain.model.response.user

import com.accessed.domain.model.response.BaseEntity

data class User(
    val name: String?,
    val email: String?,
    val avatar: String?
) : BaseEntity()