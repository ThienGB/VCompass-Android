package com.example.gotravel.ui.module.admin.main

import AccommodationDao
import BookingDao
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.model.User
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainAdminViewModel (private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private var accomDao: AccommodationDao = AccommodationDao()
    private var bookingDao: BookingDao = BookingDao()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _accommodations = MutableStateFlow<List<Accommodation>>(emptyList())
    val accommodations: StateFlow<List<Accommodation>> get() = _accommodations

    private val _accommodation = MutableStateFlow(Accommodation())
    val accommodation: StateFlow<Accommodation> get() = _accommodation

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isShowBottomBar = MutableStateFlow(true)
    val isShowBottomBar : StateFlow<Boolean> get() = _isShowBottomBar

    private var user: User = User()
    fun fetchData(){
        _isLoading.value = true
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                getAllAccom()
            }
            firestoreDataManager.fetchBooking{
               // getAllUser()
            }
        }
    }
    fun setUser(user: User) {
        this.user = user
    }

    fun setAccommodation(accommodation: Accommodation) {
        _accommodation.value = accommodation.copy()
    }
    fun setIsShowBottomBar(isShow: Boolean) {
        _isShowBottomBar.value = isShow
    }
    private fun getAllAccom() {
        viewModelScope.launch {
            val accom = accomDao.getAllAccommodations()
            if (accom.isNotEmpty()) {
                _accommodations.value = accom
                _isLoading.value = false
            } else {
                _isLoading.value = false
                Log.d("AccommodationDetail", "No booking found")
            }
        }
    }
//    private fun getAllUser() {
//        viewModelScope.launch {
//            val booking = bookingDao.getBookings()
//            if (booking.isNotEmpty()) {
//                users.value = booking
//            } else {
//                Log.d("AccommodationDetail", "No booking found")
//            }
//        }
//    }
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
}