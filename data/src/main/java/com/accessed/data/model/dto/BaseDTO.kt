package com.accessed.data.model.dto

import com.accessed.domain.model.response.BaseEntity
import com.google.gson.annotations.SerializedName

open class BaseDTO {
    @SerializedName("_id")
    var id: String? = null
    @SerializedName("id")
    var uid: String? = null
    @SerializedName("createdAt")
    var createdAt: String? = null
    @SerializedName("updatedAt")
    var updatedAt: String? = null
}

fun BaseDTO.getIdAsStringIfAny(): String? {
    return id ?: uid
}

fun BaseDTO.idAsString(): String? {
    return id
}

fun BaseDTO.uidAsString(): String? {
    return uid
}

fun <T : BaseEntity> T.withBase(dto: BaseDTO): T {
    this.id = dto.id
    this.uid = dto.uid
    this.createdAt = dto.createdAt
    this.updatedAt = dto.updatedAt
    return this
}