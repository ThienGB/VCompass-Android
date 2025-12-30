package com.vcompass.presentation.model.login

data class TokenUiModel(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val expiresIn: Long? = null,
)