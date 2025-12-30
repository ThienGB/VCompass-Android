package com.vcompass.domain.model.response.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFavoriteModel(
    val schedule: List<String>?,
    val accommodation: List<String>?,
    val attraction: List<String>?,
    val foodPlace: List<String>?,
) : Parcelable