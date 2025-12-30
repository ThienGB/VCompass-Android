package com.vcompass.domain.model.response.login

import com.vcompass.domain.model.response.user.UserModel

data class LoginModel(
    val tokens: TokenModel? = null,
    val user: UserModel? = null
)