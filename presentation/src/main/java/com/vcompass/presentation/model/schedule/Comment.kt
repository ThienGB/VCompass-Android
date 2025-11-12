package com.vcompass.presentation.model.schedule

import android.os.Parcelable
import com.vcompass.presentation.model.Base
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment(
    val idUser: String?,
    val content: String?,
    val replies: List<Comment>?,
) : Base(), Parcelable
