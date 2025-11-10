package com.vcompass.domain.model.response.user

import android.os.Parcelable
import com.vcompass.domain.model.response.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String? = null,
    var email: String? = null,
    val avatar: String? = null
) : BaseModel(), Parcelable