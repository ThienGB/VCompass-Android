package com.example.gotravel.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserAccount {
    var userId: String? = null
    var fullName: String? = null
    var email: String? = null
    var role: String? = null
    var phone: String? = null
}

