package com.vcompass.data.model.dto.party.login

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("accessToken")
    val accessToken: String? = null,
    @SerializedName("refreshToken")
    val refreshToken: String? = null,
    @SerializedName("expiresIn")
    val expiresIn: Long? = null,
)