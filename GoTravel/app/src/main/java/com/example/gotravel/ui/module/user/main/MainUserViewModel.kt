package com.example.gotravel.ui.module.user.main

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
import java.util.Date
import java.util.UUID

class MainUserViewModel(private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private val firestoreNotiManager = FirestoreNotiManager()
    private val firestoreUserManager = UserFirestoreDataManager()
    private val firestoreConverManager = FirestoreConverManager(viewModelScope)
    private var accomDao: AccommodationDao = AccommodationDao()
    private var notificationDao: NotificationDao = NotificationDao()
    private var conversationDao: ConversationDao = ConversationDao()
    private var bookingDao: BookingDao = BookingDao()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _searchData = MutableStateFlow(Search())
    val searchData: StateFlow<Search> get() = _searchData

    private val _accommodations = MutableStateFlow<List<Accommodation>>(emptyList())
    val accommodations: StateFlow<List<Accommodation>> get() = _accommodations

    private val _accommodation = MutableStateFlow(Accommodation())
    val accommodation: StateFlow<Accommodation> get() = _accommodation

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> get() = _notifications

    private val _notification = MutableStateFlow(Notification())
    val notification: StateFlow<Notification> get() = _notification

    private val _room = MutableStateFlow(Room())
    val room: StateFlow<Room> get() = _room

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isShowBottomBar = MutableStateFlow(true)
    val isShowBottomBar : StateFlow<Boolean> get() = _isShowBottomBar

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> get() = _conversations

    private val _conversation = MutableStateFlow(Conversation())
    val conversation: StateFlow<Conversation> get() = _conversation

    private var bookings: List<Booking> = emptyList()
    private val _user = MutableStateFlow(UserAccount())
    val user: StateFlow<UserAccount> get() = _user

    fun setSearchData(data: Search) {
        _searchData.value = data.copy()
    }
    fun setUser(user: UserAccount){
        _user.value = user.copy()
    }
    fun setNotification(notification: Notification) {
        _notification.value = notification.copy()
    }
    fun fetchHighPriorityData(){
        viewModelScope.launch {
            firestoreNotiManager.fetchNotifications(_user.value.userId
            ) { fetchNotificationsFromRealm() }
        }
        firestoreNotiManager.listenToNotifications(_user.value.userId) { fetchNotificationsFromRealm() }
        viewModelScope.launch {
            firestoreConverManager.fetchConversation(_user.value.userId
            ) { fetchConversationsFromRealm()
                listenMessage()
            }
        }
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                getListAccom()
            }
        }
    }
    private fun listenMessage(){
        firestoreConverManager.listenToConversation (conversations.value) {
            fetchConversationsFromRealm()
            setConversation(conversation.value)}
    }
    fun fetchData(){
        _isLoading.value = true
        viewModelScope.launch {
            getListAccom()
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
    fun setConversation(conversation: Conversation) {
        _conversation.value = conversation.copy()
    }
    // Home & Bookinggg
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
                                booking.roomId == room.roomId && room.status == "Active" &&
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
    fun handleConfirmBooking(accommodation: Accommodation, room: Room, user: UserAccount, search: Search, phone: String, name: String, userEmail: String){
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
        val conversation = Conversation().apply {
            id_conversation = UUID.randomUUID().toString().take(15)
            idFirstUser = _user.value.userId
            idSecondUser = accommodation.partnerId
            createdAt = Date()
        }
        val notification = Notification().apply {
            id_notification = UUID.randomUUID().toString().take(15)
            id_sender = accommodation.partnerId
            id_receiver = _user.value.userId
            title = "Thông báo đặt phòng thành công"
            content = "Bạn đã đặt phòng thành công tại ${accommodation.name}, yêu cầu của bạn đang được xét duyệt." +
                    "Bạn sẽ sớm nhận được thông báo." +
                    "Nếu có bất kỳ thắc mắc nào, hãy nhắn tin cho chúng tôi vì chúng ta đã được kết nối với nhau"
            isRead = "false"
            type = "noti"
            create_at = Date()
        }
        val notificationToPartner = Notification().apply {
            id_notification = UUID.randomUUID().toString().take(15)
            id_sender = accommodation.partnerId
            id_receiver = accommodation.partnerId
            title = "Thông báo có đăt phòng mới"
            content = "Khách hàng ${_user.value.fullName} vừa đặt phòng cho ${search.vacationDays} ngày, " +
                    "${search.guests} khách, tại ${accommodation.name}"
            isRead = "false"
            type = "noti"
            create_at = Date()
        }
        viewModelScope.launch {
            bookingDao.insertOrUpdateBooking(booking) {
                firestoreDataManager.addBookingToFirestore(booking)
            }
            firestoreNotiManager.addNotification(notification)
            firestoreNotiManager.addNotification(notificationToPartner)
            firestoreConverManager.addConversationToFirestore(conversation){
                fetchHighPriorityData()
            }
        }
    }

    // Notification
    private fun fetchNotificationsFromRealm() {
        viewModelScope.launch {
            val notifications = notificationDao.getAllNotifications()
            if (notifications.isNotEmpty()) {
                _notifications.value = notifications.map { it.copy() }
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
                _isLoading.value = false
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
            this.image = user.image
        }
    }
    fun logout() {
        auth.signOut()
        _user.value = UserAccount()
        _conversations.value = emptyList()
        conversationDao.deleteAllConversations()
    }

    override fun onCleared() {
        super.onCleared()
        realmHelper.closeRealm()
        firestoreNotiManager.removeListener()
    }
}