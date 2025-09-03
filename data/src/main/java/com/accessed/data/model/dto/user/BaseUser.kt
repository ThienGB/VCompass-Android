package com.accessed.data.model.dto.user

import com.accessed.data.model.dto.BaseDTO
import com.accessed.data.model.dto.MapperDTO
import com.accessed.data.model.dto.withBase
import com.accessed.domain.model.response.user.User
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