package com.example.gotravel.ui.module.admin.main

import AccommodationDao
import BookingDao
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.local.dao.UserDao
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.model.User
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.data.remote.UserFirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class MainAdminViewModel (private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private val userFirestoreDataManager = UserFirestoreDataManager()
    private var accomDao: AccommodationDao = AccommodationDao()
    private var userDao: UserDao = UserDao()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _accommodations = MutableStateFlow<List<Accommodation>>(emptyList())
    val accommodations: StateFlow<List<Accommodation>> get() = _accommodations

    private val _accommodation = MutableStateFlow(Accommodation())
    val accommodation: StateFlow<Accommodation> get() = _accommodation

    private val _currentUser = MutableStateFlow(User())
    val currentUser: StateFlow<User> get() = _currentUser

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isShowBottomBar = MutableStateFlow(true)
    val isShowBottomBar : StateFlow<Boolean> get() = _isShowBottomBar

    private var user: UserAccount = UserAccount()
    fun fetchData(){
        _isLoading.value = true
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                getAllAccom()
            }
            userFirestoreDataManager.fetchUser{
                getAllUser()
            }
        }
    }
    fun fetchUser() {
        _isLoading.value = true
        viewModelScope.launch {
            userFirestoreDataManager.fetchUser {
                getAllUser()
            }
        }
    }
    fun setUser(user: UserAccount) {
        this.user = user
    }
    fun setCurrentUser(user: User) {
        _currentUser.value = user.copy()
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
    private fun getAllUser() {
        viewModelScope.launch {
            val userList = userDao.getAllUsers()
            if (userList.isNotEmpty()) {
                _isLoading.value = false
                _users.value = userList
            } else {
                _isLoading.value = false
                Log.d("AccommodationDetail", "No booking found")
            }
        }
    }
    fun handleConfirmAccom(status: String){
        val accom = Accommodation().apply {
            accommodationId = accommodation.value.accommodationId
            partnerId = accommodation.value.partnerId
            this.name = accommodation.value.name
            this.image = accommodation.value.image
            this.description = accommodation.value.description
            this.address = accommodation.value.address
            this.city = accommodation.value.city
            this.amentities = accommodation.value.amentities
            this.status = status
        }
        firestoreDataManager.addAccommodationToFirestore(accom)
        accomDao.insertOrUpdateAccomm(accom){fetchData()}
    }
    fun handleBanUser(status: String){
        val newUser = User().apply {
            userId = currentUser.value.userId
            fullName= currentUser.value.fullName
            email = currentUser.value.email
            phone = currentUser.value.phone
            role = currentUser.value.role
            this.status = status
        }
        viewModelScope.launch {
            userFirestoreDataManager.updateStatus(currentUser.value.userId, status)
            userDao.insertOrUpdateUser(newUser){fetchUser()}
        }
    }
}