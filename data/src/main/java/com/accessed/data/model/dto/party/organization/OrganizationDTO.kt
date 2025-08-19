package com.accessed.data.model.dto.party.organization

import com.accessed.data.model.dto.party.ParentPartyDTO
import com.accessed.data.model.dto.party.primary.PrimaryAddressDTO
import com.google.gson.annotations.SerializedName

class OrganizationDTO : ParentPartyDTO() {

    @SerializedName("images")
    val images: List<Any>? = null

    @SerializedName("videos")
    val videos: List<Any>? = null

    @SerializedName("primaryAddress")
    val primaryAddress: PrimaryAddressDTO? = null
}