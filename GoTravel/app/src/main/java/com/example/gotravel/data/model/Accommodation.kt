package com.example.gotravel.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Accommodation : RealmObject() {
    @PrimaryKey
    var accommodationId: String? = null
    var partnerId: String? = null
    var name: String? = null
    var price: Int = 0
    var image: String? = null
    var description: String? = null
    var address: String? = null
    var longitude: String? = null
    var latitude: String? = null
    var cityId: String? = null

    fun copy(): Accommodation {
        val copy = Accommodation()
        copy.accommodationId = this.accommodationId
        copy.partnerId = this.partnerId
        copy.name = this.name
        copy.price = this.price
        copy.image = this.image
        copy.description = this.description
        copy.address = this.address
        copy.longitude = this.longitude
        copy.latitude = this.latitude
        copy.cityId = this.cityId
        return copy
    }
}
