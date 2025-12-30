package com.vcompass.domain.model.response.user

import android.os.Parcelable
import com.vcompass.domain.model.response.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String? = null,
    var email: String? = null,
    val avatar: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val status: String? = null,
    val role: String? = null,
    val favorites: UserFavoriteModel? = null
) : BaseModel(), Parcelable