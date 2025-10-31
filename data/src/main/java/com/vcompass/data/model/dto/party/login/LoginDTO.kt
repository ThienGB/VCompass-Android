package com.vcompass.data.model.dto.party.login

import com.vcompass.domain.model.response.login.LoginModel
import com.google.gson.annotations.SerializedName

data class LoginDTO(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: LoginUserDTO? = null
)