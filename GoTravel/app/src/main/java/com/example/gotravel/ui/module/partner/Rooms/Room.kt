package com.example.gotravel.ui.module.partner.Rooms

import com.example.gotravel.R

data class Room(
    val roomNumber: String,
    val roomType: String,
    val price: Double,
    val capacity: Int,
    val features: List<String>,
    val image: Image,
    val availability: Availability,
    val createdAt: String,
    val updatedAt: String
)

data class Image(
    val url: String,
    val alt: String
)

data class Availability(
    val isAvailable: Boolean
)
