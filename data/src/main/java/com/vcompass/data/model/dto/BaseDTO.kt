package com.vcompass.data.model.dto

import com.google.gson.annotations.SerializedName

open class BaseDTO {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("_id")
    var uid: String? = null
    @SerializedName("createdAt")
    var createdAt: String? = null
    @SerializedName("updatedAt")
    var updatedAt: String? = null
}