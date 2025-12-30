package com.vcompass.data.model.dto.party.login

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.user.UserDTO
import com.vcompass.domain.model.response.login.LoginModel
import com.vcompass.domain.util.tryParseObject

data class LoginDTO(
    @SerializedName("tokens")
    val tokens: TokenDTO? = null,
    @SerializedName("user")
    val user: UserDTO? = null
)

fun LoginDTO.toLoginModel(): LoginModel{
    return tryParseObject<LoginModel>() ?: LoginModel()
}
