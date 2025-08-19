package com.accessed.data.model.dto.party.primary

import com.google.gson.annotations.SerializedName

class PrimaryAddressDTO : PrimaryInfoDTO() {
    @SerializedName("name")
    val name: String? = null

    @SerializedName("address1")
    val address1: String? = null

    @SerializedName("address2")
    val address2: String? = null

    @SerializedName("countryCode")
    val countryCode: String? = null

    @SerializedName("country")
    val country: String? = null

    @SerializedName("stateCode")
    val stateCode: String? = null

    @SerializedName("state")
    val state: String? = null

    @SerializedName("city")
    val city: String? = null

    @SerializedName("postalCode")
    val postalCode: String? = null

    @SerializedName("district")
    val district: String? = null

    @SerializedName("staticMapImageUrl")
    val staticMapImageUrl: String? = null

    @SerializedName("longitude")
    val longitude: Long? = null

    @SerializedName("latitude")
    val latitude: Long? = null
}