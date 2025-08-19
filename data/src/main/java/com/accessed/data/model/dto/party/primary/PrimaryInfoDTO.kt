package com.accessed.data.model.dto.party.primary

import com.accessed.data.model.dto.BaseDTO
import com.google.gson.annotations.SerializedName

open class PrimaryInfoDTO : BaseDTO() {
    @SerializedName("type")
    val type: String? = null

    @SerializedName("value")
    val value: String? = null

    @SerializedName("isPrimary")
    val isPrimary: Boolean = false

    @SerializedName("isPublic")
    val isPublic: Boolean = false
}