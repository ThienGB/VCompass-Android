package com.vcompass.presentation.model.user

import com.vcompass.domain.model.response.BaseModel
import com.vcompass.domain.model.response.user.UserFavoriteModel

data class User(
    val name: String?,
    val email: String?,
    val avatar: String?,
    val phoneNumber: String? = null,
    val address: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val status: String? = null,
    val role: String? = null,
    val favorites: UserFavoriteModel? = null
) : BaseModel()