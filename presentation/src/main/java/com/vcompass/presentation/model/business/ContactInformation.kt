package com.vcompass.presentation.model.business

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactInformation(
    val phone: String?,
    val email: String?,
    val website: String?
) : Parcelable