package com.example.gotravel.ui.module.main.partner

import AccommodationDao
import BookingDao
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Rating
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.model.Search
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainPartnerViewModel (private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private var accomDao: AccommodationDao = AccommodationDao()
    private var bookingDao: BookingDao = BookingDao()

    private val _accommodation = MutableStateFlow(Accommodation())
    val accommodation: StateFlow<Accommodation> get() = _accommodation

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> get() = _bookings

    private val _booking = MutableStateFlow(Booking())
    val booking: StateFlow<Booking> get() = _booking

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _room = MutableStateFlow(Room())
    val room: StateFlow<Room> get() = _room

    private var user: UserAccount = UserAccount()
    fun fetchData(){
        _isLoading.value = true
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                getAccomByPartner()
            }
            firestoreDataManager.fetchBooking{
                getBookingsByPartner()
            }
        }
    }
    fun setUser(user: UserAccount) {
        this.user = user
    }
    fun setRoom(room: Room) {
        _room.value = room.copy()
    }
    fun setBooking(booking: Booking){
        _booking.value = booking.copy()
    }
    private fun getAccomByPartner() {
        viewModelScope.launch {
            val accom: Accommodation? = accomDao.getAccommByPartner(user.userId)
            if (accom != null) {
                _accommodation.value = accom.copy()
                getBookingsByPartner()
                _isLoading.value = false
            } else {
                _isLoading.value = false
                Log.d("AccommodationDetail", "No booking found")
            }
        }
    }
    private fun getBookingsByPartner() {
        viewModelScope.launch {
            val booking = bookingDao.getBookingsByAccom(accommodation.value.accommodationId)
            if (booking.isNotEmpty()) {
                _bookings.value = booking
            } else {
                Log.d("AccommodationDetail", "No booking found")
            }
        }
    }
    fun insertAccom(name: String, image: String, description: String, address: String, city: String, amentities: String){
        val accom = Accommodation().apply {
            accommodationId = UUID.randomUUID().toString()
            partnerId = user.userId
            this.name = name
            this.image = image
            this.description = description
            this.address = address
            this.city = city
            this.amentities = amentities
            this.status = "pending"
        }
        firestoreDataManager.addAccommodationToFirestore(accom)
        accomDao.insertOrUpdateAccomm(accom){fetchData()}
    }
    fun updateAccom(accommodationId: String,name: String, image: String, description: String, address: String, city: String, amentities: String){
        val accom = Accommodation().apply {
            this.accommodationId = accommodationId
            partnerId = user.userId
            this.name = name
            this.image = image
            this.description = description
            this.address = address
            this.city = city
            this.amentities = amentities
            this.status = "pending"
        }
        firestoreDataManager.addAccommodationToFirestore(accom)
        accomDao.insertOrUpdateAccomm(accom){fetchData()}
    }
    fun insertRoom(roomId: String, name: String, roomType: String, price: Int, people: Int, bed: Int, image: String, area: Int, status: String){
        val newRoom = Room().apply {
            this.roomId = if (roomId != "") roomId else UUID.randomUUID().toString()
            this.name = name
            this.roomType = roomType
            this.price = price
            this.people = people
            this.bed = bed
            this.image = image
            this.area = area
            this.status = status
        }
        firestoreDataManager.updateRoomsInFirestore(accommodation.value.accommodationId, newRoom)
        accomDao.insertRoom(accommodation.value.accommodationId, newRoom){fetchData()}
    }
    fun handleConfirmBooking(status: String){
        val newBooking = Booking().apply {
            bookingId = booking.value.bookingId
            roomId = booking.value.roomId
            startDate = booking.value.startDate
            endDate = booking.value.endDate
            accommodationId = booking.value.accommodationId
            accommodationName = booking.value.accommodationName
            userId = booking.value.userId
            price = booking.value.price
            this.status = status
            phoneNumber = booking.value.phoneNumber
            fullName = booking.value.fullName
            email = booking.value.email
        }
        firestoreDataManager.addBookingToFirestore(newBooking)
        bookingDao.insertOrUpdateBooking(newBooking) {fetchData()}
    }
}