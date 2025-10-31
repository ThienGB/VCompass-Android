package com.vcompass.data.model.dto.destination

import com.google.gson.annotations.SerializedName

data class ContactInformationDTO(
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("website")
    val website: String?
)