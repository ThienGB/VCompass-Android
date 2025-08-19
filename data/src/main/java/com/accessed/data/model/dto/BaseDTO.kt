package com.accessed.data.model.dto

import com.google.gson.annotations.SerializedName

open class BaseDTO {
    @SerializedName("id")
    val id: String? = null

    @SerializedName("uuid")
    val uuid: String? = null
}

fun BaseDTO.getIdAsStringIfAny(): String? {
    return id ?: uuid
}

fun BaseDTO.idAsString(): String? {
    return id
}

fun BaseDTO.uuidAsString(): String? {
    return uuid
}