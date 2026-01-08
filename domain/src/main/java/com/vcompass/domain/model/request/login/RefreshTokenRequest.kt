package com.vcompass.domain.model.request.login

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String
)