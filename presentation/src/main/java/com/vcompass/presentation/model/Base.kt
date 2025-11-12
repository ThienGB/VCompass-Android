package com.vcompass.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Base(
    var id: String? = null,
    var createdAt: String? = null,
    var updatedAt: String? = null
) : Parcelable