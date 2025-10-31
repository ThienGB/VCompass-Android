package com.vcompass.data.model.dto.user

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

data class BaseUserDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("avatar")
    val avatar: String?
): BaseDTO()