package com.vcompass.domain.model.response.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginModel(val token: String? = null) : Parcelable