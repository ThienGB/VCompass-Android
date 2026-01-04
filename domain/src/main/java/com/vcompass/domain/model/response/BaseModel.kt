package com.vcompass.domain.model.response

import com.google.gson.annotations.SerializedName

open class BaseModel {
    var id: String? = null
    @SerializedName("_id")
    var uid: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
}