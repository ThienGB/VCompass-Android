package com.example.gotravel.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User: RealmObject() {
    @PrimaryKey
    var userId: String = ""
    var fullName: String = ""
    var email: String = ""
    var role: String = ""
    var phone: String = ""
    var status: String = ""
    var image: String = ""

    fun copy(): User {
        val copy = User()
        copy.userId = this.userId
        copy.fullName = this.fullName
        copy.email = this.email
        copy.role = this.role
        copy.phone = this.phone
        copy.status = this.status
        copy.image = this.image
        return copy
    }
}