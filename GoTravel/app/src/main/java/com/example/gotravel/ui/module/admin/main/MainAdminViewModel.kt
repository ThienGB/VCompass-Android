package com.example.gotravel.ui.module.admin.main

import AccommodationDao
import BookingDao
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotravel.data.local.ConversationDao
import com.example.gotravel.data.local.dao.NotificationDao
import com.example.gotravel.data.local.dao.UserDao
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Conversation
import com.example.gotravel.data.model.Message
import com.example.gotravel.data.model.Notification
import com.example.gotravel.data.model.Room
import com.example.gotravel.data.model.User
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

class MainAdminViewModel (private val realmHelper: RealmHelper) : ViewModel() {
    private val firestoreDataManager = FirestoreDataManager()
    private val firestoreNotiManager = FirestoreNotiManager()
    private val firestoreUserManager = UserFirestoreDataManager()
    private val firestoreConverManager = FirestoreConverManager(viewModelScope)
    private var accomDao: AccommodationDao = AccommodationDao()
    private var bookingDao: BookingDao = BookingDao()
    private var notificationDao: NotificationDao = NotificationDao()
    private var conversationDao: ConversationDao = ConversationDao()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
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

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> get() = _conversations

    private val _conversation = MutableStateFlow(Conversation())
    val conversation: StateFlow<Conversation> get() = _conversation

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> get() = _notifications

    private val _notification = MutableStateFlow(Notification())
    val notification: StateFlow<Notification> get() = _notification

    private val _user = MutableStateFlow(UserAccount())
    val user: StateFlow<UserAccount> get() = _user
    fun fetchData(){
        _isLoading.value = true
        viewModelScope.launch {
            firestoreDataManager.fetchAccommodation {
                getAllAccom()
            }
            firestoreUserManager.fetchUser{
                getAllUser()
            }
        }
    }
    private fun fetchUser() {
        _isLoading.value = true
        viewModelScope.launch {
            firestoreUserManager.fetchUser {
                getAllUser()
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
        firestoreNotiManager.listenToNotifications(_user.value.userId) { fetchNotificationsFromRealm() }
        viewModelScope.launch {
            firestoreConverManager.fetchConversation(_user.value.userId
            ) {
                _conversations.value = emptyList()
                fetchConversationsFromRealm()
                listenMessage()}
        }

    }
    fun setUser(user: UserAccount) {
        _user.value = user.copy()
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
    fun setConversation(conversation: Conversation) {
        _conversation.value = conversation.copy()
    }
    fun setNotification(notification: Notification) {
        _notification.value = notification.copy()
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
                _users.value = userList
                fetchHighPriorityData()
                _isLoading.value = false
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
        val acceptContent = "Chỗ ở của bạn đã được xét duyệt," +
                " giờ đây bạn có thể bắt đầu kinh doanh bằng cách thêm phòng," +
                " hệ thống sẽ tự động giúp bạn tìm những khách hàng tiềm năng," +
                " chúc may mắn và cảm ơn đã sử dụng dịch vụ."
        val rejectContent = "Chúng tôi rất tiếc, nhưng sau quá trình xem xét và đánh giá " +
                "thì chỗ ở của bạn không được xét duyệt vì một số lý do như" +
                " vị trí chưa chính xác, quy mô quá nhỏ,... " +
                "Bạn có thể sửa lại thông tin và chúng tôi sẵn lòng xem xét lần nữa"
        val notification = Notification().apply {
            id_notification = UUID.randomUUID().toString().take(15)
            id_sender = _user.value.userId
            id_receiver = accommodation.value.partnerId
            title = "Thông báo duyệt chỗ ở" + if (status == "accept") " thành công" else " thất bại"
            content = if (status == "accept") acceptContent else rejectContent
            isRead = "false"
            type = "accom"
            create_at = Date()
        }
        val conversation = Conversation().apply {
            id_conversation = UUID.randomUUID().toString().take(15)
            idFirstUser = _user.value.userId
            idSecondUser = accommodation.value.partnerId
            createdAt = Date()
        }
        firestoreDataManager.addAccommodationToFirestore(accom)
        accomDao.insertOrUpdateAccomm(accom){fetchData()}
        firestoreNotiManager.addNotification(notification)
        firestoreConverManager.addConversationToFirestore(conversation){
            fetchHighPriorityData()
        }
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
            firestoreUserManager.updateStatus(currentUser.value.userId, status)
            userDao.insertOrUpdateUser(newUser){fetchUser()}
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
            }else{
                _conversations.value = emptyList()
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