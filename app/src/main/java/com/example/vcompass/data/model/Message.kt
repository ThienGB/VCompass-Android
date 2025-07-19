package com.example.vcompass.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Date

open class Message : RealmObject() {
    @PrimaryKey
    var id_message: String? = null
    var id_user: String? = null
    var content: String? = null
    var createdAt: Date? = null

    fun copy(): Message {
        val copy = Message()
        copy.id_message = this.id_message
        copy.content = this.content
        copy.createdAt = this.createdAt
        copy.id_user = this.id_user
        return copy
    }
}
