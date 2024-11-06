package com.example.gotravel.data.model

data class Search(
    var destination: String = "",
    var departureDate: Long = System.currentTimeMillis(),
    var returnDate: Long = System.currentTimeMillis() + (24 * 60 * 60 * 1000),
    var vacationDays: Int = 1,
    var guests: Int = 0,
    var rooms: Int = 0,
    var minPrice: Int = 0,
    var maxPrice: Int = 0
)