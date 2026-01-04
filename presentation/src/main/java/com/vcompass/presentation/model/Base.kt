package com.vcompass.presentation.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
open class Base(
    var id: String? = null,
    @SerializedName("_id")
    var uid: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
) : Parcelable

fun Base.getIdOrUid(): String = id ?: uid ?: ""