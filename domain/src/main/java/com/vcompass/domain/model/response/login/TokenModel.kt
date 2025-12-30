package com.vcompass.domain.model.response.login

data class TokenModel(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresIn: Long? = null,
)