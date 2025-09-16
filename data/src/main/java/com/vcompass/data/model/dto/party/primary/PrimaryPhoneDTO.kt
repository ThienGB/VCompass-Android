package com.vcompass.data.model.dto.party.primary

import com.google.gson.annotations.SerializedName

class PrimaryPhoneDTO : PrimaryInfoDTO() {
    @SerializedName("isoCode2")
    val isoCode2: String? = null

    @SerializedName("countryCode")
    val countryCode: String? = null
}