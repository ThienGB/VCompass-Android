package com.example.gotravel.ui.module.main.partner

import AccommodationDao
import BookingDao
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.local.ConversationDao
import com.example.gotravel.data.local.dao.NotificationDao
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Conversation
import com.example.gotravel.data.model.Message
import com.example.gotravel.data.model.Notification
import com.example.gotravel.data.model.Rating
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.model.Search
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.data.remote.FirestoreConverManager
import com.example.gotravel.data.remote.FirestoreDataManager
import com.example.gotravel.data.remote.FirestoreNotiManager
import com.example.gotravel.data.remote.UserFirestoreDataManager
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.helper.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainPartnerViewModel (private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private val firestoreNotiManager = FirestoreNotiManager()
    private val firestoreUserManager = UserFirestoreDataManager()
    private val firestoreConverManager = FirestoreConverManager(viewModelScope)
    private var accomDao: AccommodationDao = AccommodationDao()
    private var bookingDao: BookingDao = BookingDao()
    private var notificationDao: NotificationDao = NotificationDao()
    private var conversationDao: ConversationDao = ConversationDao()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> get() = _conversations

    private val _conversation = MutableStateFlow(Conversation())
    val conversation: StateFlow<Conversation> get() = _conversation

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> get() = _notifications

    private val _notification = MutableStateFlow(Notification())
    val notification: StateFlow<Notification> get() = _notification

    private val _isShowBottomBar = MutableStateFlow(true)
    val isShowBottomBar : StateFlow<Boolean> get() = _isShowBottomBar

    private val _user = MutableStateFlow(UserAccount())
    val user: StateFlow<UserAccount> get() = _user
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
    private fun listenMessage(){
        firestoreConverManager.listenToConversation (conversations.value) {
            fetchConversationsFromRealm()
            setConversation(conversation.value)}
    }
    fun fetchHighPriorityData(){
        viewModelScope.launch {
            firestoreNotiManager.fetchNotifications(_user.value.userId
            ) { fetchNotificationsFromRealm() }
        }
        firestoreNotiManager.listenToNotifications { fetchNotificationsFromRealm() }
        viewModelScope.launch {
            firestoreConverManager.fetchConversation(_user.value.userId
            ) { fetchConversationsFromRealm()
                listenMessage()
            }
        }
    }
    fun setUser(user: UserAccount) {
        _user.value = user
    }
    fun setRoom(room: Room) {
        _room.value = room.copy()
    }
    fun setBooking(booking: Booking){
        _booking.value = booking.copy()
    }
    fun setConversation(conversation: Conversation) {
        _conversation.value = conversation.copy()
    }
    fun setIsShowBottomBar(isShow: Boolean) {
        _isShowBottomBar.value = isShow
    }
    fun setNotification(notification: Notification) {
        _notification.value = notification.copy()
    }
    private fun getAccomByPartner() {
        viewModelScope.launch {
            val accom: Accommodation? = accomDao.getAccommByPartner(_user.value.userId)
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
            partnerId = _user.value.userId
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
            partnerId = _user.value.userId
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
    // Notification
    private fun fetchNotificationsFromRealm() {
        viewModelScope.launch {
            val notifications = notificationDao.getAllNotifications()
            if (notifications.isNotEmpty()) {
                _notifications.value = notifications
            } else {
                _notifications.value = emptyList()
            }
        }
    }
    fun deleteAllNotifications() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                firestoreNotiManager.deleteAllNotifications(_user.value.userId)
            }
            Log.d("AccommodationDetail", "All notifications deleted.")
            fetchNotificationsFromRealm()

        }
    }
    fun changeNotificationStatus(id: String) {
        Log.d("AccommodationDetail", "All notifications update.")
        viewModelScope.launch {
            firestoreNotiManager.changeStatus(id)
            Log.d("AccommodationDetail", "All notifications update.")
            fetchNotificationsFromRealm()
        }
    }

    // Conversation
    private fun fetchConversationsFromRealm() {
        viewModelScope.launch {
            val conversations = conversationDao.getAllConversations(_user.value.userId)
            if (conversations.isNotEmpty()) {
                _conversations.value = conversations
            }
        }
    }
    fun sendMessage(message: Message) {
        viewModelScope.launch {
            firestoreConverManager.sendMessage(conversation.value.id_conversation,message)
        }
    }

    // Profile
    fun updateUser(user: UserAccount, context: Context) {
        viewModelScope.launch {
            firestoreUserManager.editProfile(user)
        }
        val sharedPreferences = context.getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("fullName", user.fullName)
        editor.putString("phone", user.phone)
        editor.apply()
        _user.value.apply {
            this.fullName = user.fullName
            this.phone = user.phone
        }
    }
    fun logout() {
        auth.signOut()
        _user.value = UserAccount()
    }
    override fun onCleared() {
        super.onCleared()
        realmHelper.closeRealm()
        firestoreNotiManager.removeListener()
    }
}