package com.example.gotravel.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Rating : RealmObject() {
    @PrimaryKey
    var ratingId: String = ""
    var userId: String = ""
    var content: String = ""
    var response: String = ""
    var responseTime: Long = 0
    var rate: Int = 0
    var userName: String = ""
    var createdAt: Long = System.currentTimeMillis()

    fun copy(): Rating {
        val copy = Rating()
        copy.ratingId = this.ratingId
        copy.userId = this.userId
        copy.content = this.content
        copy.rate = this.rate
        copy.userName= this.userName
        copy.createdAt = this.createdAt
        return copy
    }
}