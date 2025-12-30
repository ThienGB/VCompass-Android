package com.vcompass.presentation.model.login

import com.vcompass.domain.model.response.login.LoginModel
import com.vcompass.domain.model.response.login.TokenModel
import com.vcompass.domain.model.response.user.UserModel
import com.vcompass.presentation.util.tryParseObject

data class LoginUiModel(
    val tokens: TokenModel? = null,
    val user: UserModel? = null
)

fun LoginModel.toLoginUiModel(): LoginUiModel {
    return tryParseObject<LoginUiModel>() ?: LoginUiModel()
}