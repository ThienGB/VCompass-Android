package com.example.vcompass.data.model

data class Search(
    var destination: String = "",
    var departureDate: Long = System.currentTimeMillis(),
    var returnDate: Long = System.currentTimeMillis() + (24 * 60 * 60 * 1000),
    var vacationDays: Int = 1,
    var guests: Int = 2,
    var rooms: Int = 1,
    var minPrice: Int = 0,
    var maxPrice: Int = 1800000
)