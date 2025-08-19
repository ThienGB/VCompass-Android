package com.accessed.data.model.dto.party.login

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.login.LoginModel
import com.google.gson.annotations.SerializedName

data class LoginDTO(
    @SerializedName("token")
    val token: String? = null,
    @SerializedName("user")
    val user: LoginUserDTO? = null
) : MapperDTO<LoginModel> {

    override fun toDomain(): LoginModel {
        return LoginModel(token)
    }
}