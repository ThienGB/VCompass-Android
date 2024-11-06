package com.example.gotravel.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Room : RealmObject() {
    @PrimaryKey
    var roomId: String = ""
    var name: String = ""
    var roomType: String = ""
    var price: Int = 0
    var people: Int = 0
    var bed: Int = 0
    var status:  String = ""

    fun copy(): Room {
        val copy = Room()
        copy.roomId = this.roomId
        copy.name = this.name
        copy.roomType = this.roomType
        copy.price = this.price
        copy.people = this.people
        copy.bed = this.bed
        copy.status = this.status
        return copy
    }
}