package com.vcompass.data.model.dto.party

import com.google.gson.annotations.SerializedName

class PrivacyDTO {

    @SerializedName("connection")
    val connection: String? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("follow")
    val follow: String? = null

    @SerializedName("viewAvailable")
    val viewAvailable: String? = null

    @SerializedName("viewEmail")
    val viewEmail: String? = null

    @SerializedName("viewPhone")
    val viewPhone: String? = null

    @SerializedName("viewProfile")
    val viewProfile: String? = null

    @SerializedName("viewOthersProfile")
    val viewOthersProfile: String? = null

    @SerializedName("searchProfile")
    val searchProfile: String? = null

    @SerializedName("requiredEmail")
    val requiredEmail: Boolean? = false

    @SerializedName("allowConnect")
    val allowConnect: Boolean? = false

    @SerializedName("allowMessage")
    val allowMessage: Boolean? = false

    @SerializedName("allowFollow")
    val allowFollow: Boolean? = false

    @SerializedName("allowViewOnline")
    val allowViewOnline: Boolean? = false

    @SerializedName("allowViewEmail")
    val allowViewEmail: Boolean? = false

    @SerializedName("allowViewPhone")
    val allowViewPhone: Boolean? = false

    @SerializedName("allowViewProfile")
    val allowViewProfile: Boolean? = false
}