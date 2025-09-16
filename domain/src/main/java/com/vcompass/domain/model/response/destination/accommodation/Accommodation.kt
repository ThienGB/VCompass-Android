package com.vcompass.domain.model.response.destination.accommodation

import com.vcompass.domain.model.response.destination.Destination

data class Accommodation(
    val price: Int?,
    val note: String?,
    val roomTypes: List<RoomType>?,
): Destination()

