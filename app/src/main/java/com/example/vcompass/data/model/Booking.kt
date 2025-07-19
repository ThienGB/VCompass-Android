package com.example.vcompass.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Booking : RealmObject() {
    @PrimaryKey
    var bookingId: String = ""
    var roomId: String = ""
    var startDate: Long = 0
    var endDate: Long = 0
    var accommodationId: String = ""
    var accommodationName: String = ""
    var userId: String = ""
    var price: Int = 5
    var status: String = ""
    var phoneNumber: String = ""
    var fullName: String = ""
    var email: String = ""

    fun copy(): Booking {
        val copy = Booking()
        copy.bookingId = this.bookingId
        copy.roomId = this.roomId
        copy.startDate = this.startDate
        copy.endDate = this.endDate
        copy.accommodationId = this.accommodationId
        copy.userId = this.userId
        copy.price = this.price
        copy.status = this.status
        copy.phoneNumber = this.phoneNumber
        copy.accommodationName = this.accommodationName
        copy.fullName = this.fullName
        copy.email = this.email
        return copy
    }
}