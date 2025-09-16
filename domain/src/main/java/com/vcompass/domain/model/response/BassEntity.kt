package com.vcompass.domain.model.response

open class BaseEntity {
    var id: String? = null
    var uid: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
}

fun BaseEntity.getIdAsStringIfAny(): String? {
    return id ?: uid
}

fun BaseEntity.idAsString(): String? {
    return id
}

fun BaseEntity.uidAsString(): String? {
    return uid
}