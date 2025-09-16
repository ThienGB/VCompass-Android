package com.vcompass.data.model.dto.user

import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.data.model.dto.withBase
import com.vcompass.domain.model.response.user.User
import com.google.gson.annotations.SerializedName

data class BaseUserDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("avatar")
    val avatar: String?
): BaseDTO(), MapperDTO<User> {
    override fun toDomain(): User {
        return User(
            name = name,
            email = email,
            avatar = avatar
        ).withBase(this)
    }
}