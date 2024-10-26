package com.example.gotravel.helper

import com.example.gotravel.data.model.Accommodation

interface CallbackHelper {
    fun onRoomChecked(isAvailable: Boolean) {}
    fun onDataReceived(bookedRoom: Int) {}
    fun onComplete() {}
    fun onListFavoriteRecieved(favorites: List<Favorite?>?) {}
    fun onAccommodationRecieved(accommodation: Accommodation?) {}
    fun onListAccomRecieved(accommodations: List<Accommodation?>?) {}
}
