package com.vcompass.presentation.model.user

import android.os.Parcelable
import com.vcompass.domain.model.response.user.UserFavoriteModel
import com.vcompass.domain.model.response.user.UserModel
import com.vcompass.presentation.model.Base
import com.vcompass.presentation.util.tryParseObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String? = null,
    val email: String? = null,
    val avatar: String? = null,
    val phoneNumber: String? = null,
    val address: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val status: String? = null,
    val role: String? = null,
    val favorites: UserFavoriteModel? = null
) : Base(), Parcelable

fun UserModel.toUser(): User {
    return tryParseObject<User>() ?: User()
}