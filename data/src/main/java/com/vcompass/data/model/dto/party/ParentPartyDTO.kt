package com.vcompass.data.model.dto.party

import com.vcompass.data.model.dto.BaseDTO
import com.google.gson.annotations.SerializedName

open class ParentPartyDTO() : BaseDTO() {
    @SerializedName("name")
    val name: String? = null

    @SerializedName("partyType")
    val partyType: String? = null

    @SerializedName("qrCode")
    val qrCode: String? = null

    @SerializedName("avatar")
    val avatar: String? = null

    @SerializedName("cover")
    val cover: String? = null

    @SerializedName("profileHashCode")
    val profileHashCode: String? = null

    @SerializedName("shareUrl")
    val shareUrl: String? = null

    @SerializedName("tagName")
    val tagName: String? = null

    @SerializedName("privacy")
    val privacy: PrivacyDTO? = null
}