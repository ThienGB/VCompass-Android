//package com.example.vcompass.ui.module.partner.main
//
//import AccommodationDao
//import BookingDao
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.vcompass.data.local.ConversationDao
//import com.example.vcompass.data.local.dao.NotificationDao
//import com.example.vcompass.data.model.UserAccount
//import com.example.vcompass.data.remote.FirestoreConverManager
//import com.example.vcompass.data.remote.FirestoreDataManager
//import com.example.vcompass.data.remote.FirestoreNotiManager
//import com.example.vcompass.data.remote.UserFirestoreDataManager
//import com.example.vcompass.helper.RealmHelper
//import com.example.vcompass.helper.SharedPreferencesHelper
//import com.google.firebase.auth.FirebaseAuth
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.util.Date
//import java.util.UUID
//
//class MainPartnerViewModel (private val realmHelper: RealmHelper) : ViewModel() {
//    private val firestoreDataManager = FirestoreDataManager()
//    private val firestoreNotiManager = FirestoreNotiManager()
//    private val firestoreUserManager = UserFirestoreDataManager()
//    private val firestoreConverManager = FirestoreConverManager(viewModelScope)
//    private var accomDao: AccommodationDao = AccommodationDao()
//    private var bookingDao: BookingDao = BookingDao()
//    private var notificationDao: NotificationDao = NotificationDao()
//    private var conversationDao: ConversationDao = ConversationDao()
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//
//    private val _accommodation = MutableStateFlow(Accommodation())
//    val accommodation: StateFlow<Accommodation> get() = _accommodation
//
//    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
//    val bookings: StateFlow<List<Booking>> get() = _bookings
//
//    private val _booking = MutableStateFlow(Booking())
//    val booking: StateFlow<Booking> get() = _booking
//
//    private val _rating = MutableStateFlow(Rating())
//    val rating: StateFlow<Rating> get() = _rating
//
//    private val _isLoading = MutableStateFlow(true)
//    val isLoading: StateFlow<Boolean> get() = _isLoading
//
//    private val _room = MutableStateFlow(Room())
//    val room: StateFlow<Room> get() = _room
//
//    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
//    val conversations: StateFlow<List<Conversation>> get() = _conversations
//
//    private val _conversation = MutableStateFlow(Conversation())
//    val conversation: StateFlow<Conversation> get() = _conversation
//
//    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
//    val notifications: StateFlow<List<Notification>> get() = _notifications
//
//    private val _notification = MutableStateFlow(Notification())
//    val notification: StateFlow<Notification> get() = _notification
//
//    private val _isShowBottomBar = MutableStateFlow(true)
//    val isShowBottomBar : StateFlow<Boolean> get() = _isShowBottomBar
//
//    private val _user = MutableStateFlow(UserAccount())
//    val user: StateFlow<UserAccount> get() = _user
//    fun fetchData(){
//        _isLoading.value = true
//        viewModelScope.launch {
//            firestoreDataManager.fetchAccommodation {
//                getAccomByPartner()
//            }
//            firestoreDataManager.fetchBooking{
//                getBookingsByPartner()
//            }
//        }
//    }
//    private fun listenMessage(){
//        firestoreConverManager.listenToConversation (conversations.value) {
//            fetchConversationsFromRealm()
//            setConversation(conversation.value)}
//    }
//    fun fetchHighPriorityData(){
//        viewModelScope.launch {
//            firestoreNotiManager.fetchNotifications(_user.value.userId
//            ) { fetchNotificationsFromRealm() }
//        }
//        firestoreNotiManager.listenToNotifications(_user.value.userId) { fetchNotificationsFromRealm() }
//        viewModelScope.launch {
//            firestoreConverManager.fetchConversation(_user.value.userId
//            ) { fetchConversationsFromRealm()
//                listenMessage()
//            }
//        }
//    }
//    fun setUser(user: UserAccount) {
//        _user.value = user
//    }
//    fun setRoom(room: Room) {
//        _room.value = room.copy()
//    }
//    fun setRating(rating: Rating){
//        _rating.value = rating.copy()
//    }
//    fun setBooking(booking: Booking){
//        _booking.value = booking.copy()
//    }
//    fun setConversation(conversation: Conversation) {
//        _conversation.value = conversation.copy()
//    }
//    fun setIsShowBottomBar(isShow: Boolean) {
//        _isShowBottomBar.value = isShow
//    }
//    fun setNotification(notification: Notification) {
//        _notification.value = notification.copy()
//    }
//    private fun getAccomByPartner() {
//        viewModelScope.launch {
//            val accom: Accommodation? = accomDao.getAccommByPartner(_user.value.userId)
//            if (accom != null) {
//                _accommodation.value = accom.copy()
//                getBookingsByPartner()
//                _isLoading.value = false
//            } else {
//                _isLoading.value = false
//                Log.d("AccommodationDetail", "No booking found")
//            }
//        }
//    }
//    private fun getBookingsByPartner() {
//        viewModelScope.launch {
//            val booking = bookingDao.getBookingsByAccom(accommodation.value.accommodationId)
//            if (booking.isNotEmpty()) {
//                _bookings.value = booking
//            } else {
//                Log.d("AccommodationDetail", "No booking found")
//            }
//        }
//    }
//    fun insertAccom(name: String, image: String, description: String, address: String, city: String, amentities: String){
//        val accom = Accommodation().apply {
//            accommodationId = UUID.randomUUID().toString()
//            partnerId = _user.value.userId
//            this.name = name
//            this.image = image
//            this.description = description
//            this.address = address
//            this.city = city
//            this.amentities = amentities
//            this.status = "pending"
//        }
//        val notificationToAdmin = Notification().apply {
//            id_notification = UUID.randomUUID().toString().take(15)
//            id_sender = _user.value.userId
//            id_receiver = "La6jQlWcRWa49726l5MwsXLLJ4x1"
//            title = "Thông báo có nhà cung cấp mới"
//            content = "Bạn có một yêu  cầu mới, vui loòng xem xét và duyệt"
//            isRead = "false"
//            type = "accom"
//            create_at = Date()
//        }
//        firestoreNotiManager.addNotification(notificationToAdmin)
//        firestoreDataManager.addAccommodationToFirestore(accom)
//        accomDao.insertOrUpdateAccomm(accom){fetchData()}
//    }
//    fun updateAccom(accommodationId: String,name: String, image: String, description: String, address: String, city: String, amentities: String){
//        val accom = Accommodation().apply {
//            this.accommodationId = accommodationId
//            partnerId = _user.value.userId
//            this.name = name
//            this.image = image
//            this.description = description
//            this.address = address
//            this.city = city
//            this.amentities = amentities
//            this.status = "pending"
//        }
//        val notificationToAdmin = Notification().apply {
//            id_notification = UUID.randomUUID().toString().take(15)
//            id_sender = _user.value.userId
//            id_receiver = "La6jQlWcRWa49726l5MwsXLLJ4x1"
//            title = "Thông báo có nhà cung cấp cập nhật thông tin"
//            content = "Bạn có một yêu  cầu mới, vui lòng xem xét và duyệt"
//            isRead = "false"
//            type = "accom"
//            create_at = Date()
//        }
//        firestoreNotiManager.addNotification(notificationToAdmin)
//        firestoreDataManager.addAccommodationToFirestore(accom)
//        accomDao.insertOrUpdateAccomm(accom){fetchData()}
//    }
//    fun insertRoom(roomId: String, name: String, roomType: String, price: Int, people: Int, bed: Int, image: String, area: Int, status: String){
//        val newRoom = Room().apply {
//            this.roomId = if (roomId != "") roomId else UUID.randomUUID().toString()
//            this.name = name
//            this.roomType = roomType
//            this.price = price
//            this.people = people
//            this.bed = bed
//            this.image = image
//            this.area = area
//            this.status = status
//        }
//        firestoreDataManager.updateRoomsInFirestore(accommodation.value.accommodationId, newRoom)
//        accomDao.insertRoom(accommodation.value.accommodationId, newRoom){fetchData()}
//    }
//    fun handleConfirmBooking(status: String){
//        val newBooking = Booking().apply {
//            bookingId = booking.value.bookingId
//            roomId = booking.value.roomId
//            startDate = booking.value.startDate
//            endDate = booking.value.endDate
//            accommodationId = booking.value.accommodationId
//            accommodationName = booking.value.accommodationName
//            userId = booking.value.userId
//            price = booking.value.price
//            this.status = status
//            phoneNumber = booking.value.phoneNumber
//            fullName = booking.value.fullName
//            email = booking.value.email
//        }
//        val acceptContent = "Chúng tôi đã xác nhận yêu cầu đặt phòng của bạn tại ${booking.value.accommodationName}," +
//                " chúc bạn có một kỳ nghỉ vui vẻ, " +
//                "đừng quên nhắn cho chúng tôi trước 15 phút checkin để chúng tôi được phục vụ bạn một cách chu đáo"
//        val rejectContent = "Chúng tôi rất tiếc khi thông báo với bạn rằng yêu cầu " +
//                "đặt phòng của bạn tại ${booking.value.accommodationName} đã bị từ chối vì một số lý do khách quan, chúng tôi" +
//                " vô cùng xin lỗi vì sự bất tiện này"
//        val notification = Notification().apply {
//            id_notification = UUID.randomUUID().toString().take(15)
//            id_sender = _user.value.userId
//            id_receiver = booking.value.userId
//            title = "Thông báo duyệt đăt phòng" + if (status == "success") " thành công" else " thất bại"
//            content = if (status == "accept") acceptContent else rejectContent
//            isRead = "false"
//            type = "booking"
//            create_at = Date()
//        }
//
//        firestoreNotiManager.addNotification(notification)
//        firestoreDataManager.addBookingToFirestore(newBooking)
//        bookingDao.insertOrUpdateBooking(newBooking) {fetchData()}
//    }
//    // Notification
//    private fun fetchNotificationsFromRealm() {
//        viewModelScope.launch {
//            val notifications = notificationDao.getAllNotifications()
//            if (notifications.isNotEmpty()) {
//                _notifications.value = notifications
//            } else {
//                _notifications.value = emptyList()
//            }
//        }
//    }
//    fun deleteAllNotifications() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                firestoreNotiManager.deleteAllNotifications(_user.value.userId)
//            }
//            Log.d("AccommodationDetail", "All notifications deleted.")
//            fetchNotificationsFromRealm()
//
//        }
//    }
//    fun changeNotificationStatus(id: String) {
//        Log.d("AccommodationDetail", "All notifications update.")
//        viewModelScope.launch {
//            firestoreNotiManager.changeStatus(id)
//            Log.d("AccommodationDetail", "All notifications update.")
//            fetchNotificationsFromRealm()
//        }
//    }
//
//    // Conversation
//    private fun fetchConversationsFromRealm() {
//        viewModelScope.launch {
//            val conversations = conversationDao.getAllConversations(_user.value.userId)
//            if (conversations.isNotEmpty()) {
//                _conversations.value = conversations
//            }
//        }
//    }
//    fun sendMessage(message: Message) {
//        viewModelScope.launch {
//            firestoreConverManager.sendMessage(conversation.value.id_conversation,message)
//        }
//    }
//
//    // Profile
//    fun updateUser(user: UserAccount, context: Context) {
//        viewModelScope.launch {
//            firestoreUserManager.editProfile(user)
//        }
//        val sharedPreferences = context.getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString("fullName", user.fullName)
//        editor.putString("phone", user.phone)
//        editor.apply()
//        _user.value.apply {
//            this.fullName = user.fullName
//            this.phone = user.phone
//            this.image = user.image
//        }
//    }
//    // Rating
//    fun addResponseRating(accommodationId: String, ratingId:String, response: String){
//        val responseTime = System.currentTimeMillis()
//        firestoreDataManager.updateResponseRating(accommodationId, ratingId, response, responseTime)
//        accomDao.updateRatingResponse(accommodationId, ratingId, response, responseTime) {
//            getAccomByPartner()
//        }
//    }
//
//    fun logout() {
//        auth.signOut()
//        _user.value = UserAccount()
//        _conversations.value = emptyList()
//        conversationDao.deleteAllConversations()
//    }
//    override fun onCleared() {
//        super.onCleared()
//        realmHelper.closeRealm()
//        firestoreNotiManager.removeListener()
//    }
//}