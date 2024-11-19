package com.example.gotravel.ui.module.main.user

import AccommodationDao
import BookingDao
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.model.Search
import com.example.gotravel.data.model.User
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.ui.module.partner.Booking.BookingScreen
import io.realm.RealmList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainUserViewModel(private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private var accomDao: AccommodationDao = AccommodationDao()
    private var bookingDao: BookingDao = BookingDao()

    private val _searchData = MutableStateFlow(Search())
    val searchData: StateFlow<Search> get() = _searchData

    private val _accommodations = MutableStateFlow<List<Accommodation>>(emptyList())
    val accommodations: StateFlow<List<Accommodation>> get() = _accommodations

    private val _accommodation = MutableStateFlow(Accommodation())
    val accommodation: StateFlow<Accommodation> get() = _accommodation

    private val _room = MutableStateFlow(Room())
    val room: StateFlow<Room> get() = _room

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isShowBottomBar = MutableStateFlow(true)
    val isShowBottomBar : StateFlow<Boolean> get() = _isShowBottomBar

    private var bookings: List<Booking> = emptyList()

    fun setSearchData(data: Search) {
        _searchData.value = data.copy()
    }
    fun fetchData(){
        _isLoading.value = true
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                getListAccom()
            }
            firestoreDataManager.fetchBooking{
                getListBooking()
            }
        }
    }
    fun setRoom(room: Room) {
        _room.value = room.copy()
    }
    fun setIsShowBottomBar(isShow: Boolean) {
        _isShowBottomBar.value = isShow
    }
    fun setAccommodation(accommodation: Accommodation) {
        _accommodation.value = accommodation.copy()
    }
    private fun searchAccommodations() {
        viewModelScope.launch {
            val search = _searchData.value
            val filteredAccommodations = _accommodations.value.filter { accommodation ->
                accommodation.city.contains(search.destination, ignoreCase = true) &&
                        accommodation.rooms.count { room ->
                            room.price in search.minPrice..search.maxPrice &&
                                    room.people >= search.guests / search.rooms
                        } >= search.rooms
            }.mapNotNull { accommodation ->
                val availableRooms = accommodation.rooms.filter { room ->
                    room.price in search.minPrice..search.maxPrice &&
                            room.people >= search.guests / search.rooms &&
                            bookings.none { booking ->
                                booking.roomId == room.roomId &&
                                        booking.accommodationId == accommodation.accommodationId &&
                                        !(search.returnDate <= booking.startDate || search.departureDate >= booking.endDate)
                            }
                }
                if (availableRooms.isNotEmpty()) {
                    accommodation.copy().apply {
                        rooms.clear()
                        rooms.addAll(availableRooms)
                    }
                } else {
                    null
                }
            }
            _accommodations.value = filteredAccommodations
            _isLoading.value = false
        }

    }
    private fun getListAccom() {
        viewModelScope.launch {
            val accom = accomDao.getAllAccommodations()
            if (accom.isNotEmpty()) {
                _accommodations.value = accom
            } else {
                Log.d("AccommodationDetail", "No accommodation found")
            }
        }
    }
    private fun getListBooking() {
        viewModelScope.launch {
            val booking = bookingDao.getBookings()
            if (booking.isNotEmpty()) {
                bookings = booking
                searchAccommodations()
            } else {
                _isLoading.value = false
                Log.d("AccommodationDetail", "No booking found")
            }
        }
    }
    fun handleConfirmBooking(accommodation: Accommodation, room: Room, user: User, search: Search, phone: String, name: String, userEmail: String){
        val booking = Booking().apply {
            bookingId = UUID.randomUUID().toString().take(15)
            roomId = room.roomId
            startDate = search.departureDate
            endDate = search.returnDate
            accommodationId = accommodation.accommodationId
            accommodationName = accommodation.name
            userId = user.userId
            price = room.price * search.vacationDays
            status = "pending"
            phoneNumber = phone
            fullName = name
            email = userEmail
        }
        viewModelScope.launch {
            bookingDao.insertOrUpdateBooking(booking) {
                firestoreDataManager.addBookingToFirestore(booking)
            }
        }
    }
}