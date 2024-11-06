package com.example.gotravel.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Accommodation : RealmObject() {
    @PrimaryKey
    var accommodationId: String = ""
    var partnerId: String = ""
    var name: String = ""
    var price: Int = 0
    var image: String = ""
    var description: String = ""
    var address: String = ""
    var cityId: String = ""
    var totalRate: Int = 5
    var ratings: RealmList<Rating> = RealmList()
    var rooms: RealmList<Room> = RealmList()

    fun copy(): Accommodation {
        val copy = Accommodation()
        copy.accommodationId = this.accommodationId
        copy.partnerId = this.partnerId
        copy.name = this.name
        copy.price = this.price
        copy.image = this.image
        copy.description = this.description
        copy.address = this.address
        copy.cityId = this.cityId
        copy.totalRate = this.totalRate
        copy.ratings.addAll(this.ratings.map { it.copy() })
        copy.rooms.addAll(this.rooms.map { it.copy() })
        return copy
    }
}
