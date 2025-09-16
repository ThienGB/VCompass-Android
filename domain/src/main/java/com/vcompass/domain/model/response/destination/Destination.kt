package com.vcompass.domain.model.response.destination

import com.vcompass.domain.model.rating.Rating
import com.vcompass.domain.model.response.BaseEntity
import com.vcompass.domain.model.response.destination.accommodation.Accommodation
import com.vcompass.domain.model.response.destination.ContactInformation
import com.vcompass.domain.model.response.destination.Location
import com.vcompass.domain.model.response.destination.attraction.Attraction
import com.vcompass.domain.model.response.destination.foodservice.FoodService

open class Destination(
    var name: String? = null,
    var description: String? = null,
    var location: Location? = null,
    var city: String? = null,
    var images: List<String>? = null,
    var amenities: List<String>? = null,
    var contact: ContactInformation? = null,
    var registerDate: String? = null,
    var status: String? = null,
    var ratings: List<Rating>? = null,
): BaseEntity()

fun Destination?.asAccommodation(): Accommodation? = this as? Accommodation
fun Destination?.asFoodService(): FoodService? = this as? FoodService
fun Destination?.asAttraction(): Attraction? = this as? Attraction
