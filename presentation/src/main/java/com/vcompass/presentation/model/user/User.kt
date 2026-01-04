package com.vcompass.presentation.model.user

import android.os.Parcelable
import com.vcompass.domain.model.response.user.UserFavoriteModel
import com.vcompass.presentation.model.Base
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Base(), Parcelable