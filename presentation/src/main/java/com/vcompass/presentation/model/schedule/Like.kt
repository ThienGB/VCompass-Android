package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import com.vcompass.presentation.model.Base
import kotlinx.parcelize.Parcelize

@Parcelize
data class Like(
    val idUser: String?,
) : Base(), Parcelable
