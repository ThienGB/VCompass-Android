package com.example.vcompass.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Accommodation : RealmObject() {
    @PrimaryKey
    var accommodationId: String = ""
    var partnerId: String = ""
    var name: String = ""
    var image: String = ""
    var description: String = ""
    var address: String = ""
    var city: String = ""
    var amentities =""
    var totalRate: Int = 5
    var ratings: RealmList<Rating> = RealmList()
    var rooms: RealmList<Room> = RealmList()
    var status: String = ""

    fun copy(): Accommodation {
        val copy = Accommodation()
        copy.accommodationId = this.accommodationId
        copy.partnerId = this.partnerId
        copy.name = this.name
        copy.image = this.image
        copy.description = this.description
        copy.address = this.address
        copy.city = this.city
        copy.totalRate = this.totalRate
        copy.amentities = this.amentities
        copy.status = this.status
        copy.ratings.addAll(this.ratings.map { it.copy() })
        copy.rooms.addAll(this.rooms.map { it.copy() })
        return copy
    }
}
