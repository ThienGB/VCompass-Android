package com.vcompass.domain.model.response.common

import android.os.Parcelable
import com.vcompass.domain.model.response.user.UserModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SessionDataModel(
    var isOpenedApp: Boolean? = false,
    var isRememberMe: Boolean? = false,
    var accessToken: String? = null,
    var userName: String? = null,
    var currentUser: UserModel = UserModel(),
) : Parcelable

fun SessionDataModel.isLogged() = accessToken?.isNotEmpty() == true