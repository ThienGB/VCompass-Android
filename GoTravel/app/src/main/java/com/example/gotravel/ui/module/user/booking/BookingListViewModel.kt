package com.example.gotravel.ui.module.user.booking

import AccommodationDao
import BookingDao
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Rating
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingListViewModel(private val realmHelper: RealmHelper) : ViewModel() {
    private var bookingDao: BookingDao = BookingDao()
    private var accomDao: AccommodationDao = AccommodationDao()
    private val firestoreDataManager = FirestoreDataManager()

    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> get() = _bookings

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _booking = MutableStateFlow(Booking())
    val booking: StateFlow<Booking> get() = _booking

    private val _accommodation = MutableStateFlow(Accommodation())
    val accommodation: StateFlow<Accommodation> get() = _accommodation

    private var currentUserId: String = ""

    fun setUserId(userId: String){
        currentUserId = userId
    }
    fun setBooking(booking: Booking){
        _booking.value = booking.copy()
    }
    fun fetchData(){
        _isLoading.value = true
        viewModelScope.launch {
            firestoreDataManager.fetchBooking{
                getBookingsByUser()
            }
            firestoreDataManager.fetchAccommodation {}
        }
    }
    private fun getBookingsByUser() {
        viewModelScope.launch {
            val booking = bookingDao.getBookingsByUser(currentUserId)
            if (booking.isNotEmpty()) {
                _bookings.value = booking
                _isLoading.value = false
            } else {
                _isLoading.value = false
                Log.d("BookingList", "No booking found")
            }
        }
    }
    fun getAccomById() {
        viewModelScope.launch {
            val accom = accomDao.getAccommById(booking.value.accommodationId)
            if (accom != null) {
                _accommodation.value = accom.copy()
            } else {
                Log.d("BookingList", "No booking found")
            }
        }
    }
    fun insertReview(rating: Rating){
        firestoreDataManager.updateRatingsInFirestore(booking.value.accommodationId, rating)
        accomDao.insertRating(booking.value.accommodationId, rating)
    }
    fun setAccommodation(accommodationId: String){
        val accom = accomDao.getAccommById(accommodationId)
        if (accom != null) {
            _accommodation.value = accom.copy()
        }

    }
}