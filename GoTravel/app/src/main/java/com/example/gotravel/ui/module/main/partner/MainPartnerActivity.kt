package com.example.gotravel.ui.module.main.partner

import ProfileScreen
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gotravel.MainApplication
import com.example.gotravel.helper.CommonUtils.getUserFromShareRef
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.helper.SharedPreferencesHelper
import com.example.gotravel.ui.components.Loading
import com.example.gotravel.ui.factory.ViewModelFactory
import com.example.gotravel.ui.module.accomodation.HotelDetailsScreen
import com.example.gotravel.ui.module.chat.ChatScreen
import com.example.gotravel.ui.module.chat.ConversationScreen
import com.example.gotravel.ui.module.main.login.MainLoginActivity
import com.example.gotravel.ui.module.main.user.CustomBottomBar
import com.example.gotravel.ui.module.notifications.NotificationDetail
import com.example.gotravel.ui.module.notifications.NotificationScreen
import com.example.gotravel.ui.module.partner.Booking.BookingInforPartner
import com.example.gotravel.ui.module.partner.Booking.BookingListPartner
import com.example.gotravel.ui.module.partner.DashBoard.DashboardDetail
import com.example.gotravel.ui.module.partner.DashBoard.DashboardScreen
import com.example.gotravel.ui.module.partner.Rooms.AddUpdateRoomScreen
import com.example.gotravel.ui.module.partner.accommodation.AddAccomScreen
import com.example.gotravel.ui.module.review.ListReviewScreen
import com.example.gotravel.ui.module.review.ResponseReviewScreen


class MainPartnerActivity : ComponentActivity() {
    private lateinit var viewModel: MainPartnerViewModel
    private lateinit var realmHelper: RealmHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realmHelper = (application as MainApplication).realmHelper
        val factory = ViewModelFactory(MainPartnerViewModel::class.java, realmHelper)
        viewModel = ViewModelProvider(this, factory)[MainPartnerViewModel::class.java]
        val sharedPreferences = getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
        val user = getUserFromShareRef(sharedPreferences)
        if (user.status == "banned"){
            viewModel.logout()
            val intent = Intent(this, MainLoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show()
        }
        viewModel.setUser(user)
        viewModel.fetchData()
        viewModel.fetchHighPriorityData()
        setContent {
            MainPartnerScreen(viewModel)
        }
    }
}

@Composable
fun MainPartnerScreen(
    viewModel: MainPartnerViewModel
) {
    val isShowBottomBar by viewModel.isShowBottomBar.collectAsState()
    val navController = rememberNavController()
    val isLoading by viewModel.isLoading.collectAsState()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedRoute = currentBackStackEntry?.destination?.route ?: "home"
    if (isLoading)
        Loading()
    else {
        Scaffold(
            bottomBar = {
                if (isShowBottomBar)
                    CustomBottomBar(navController, selectedRoute)
            }
        ) { paddingValues ->
            NavHostPartnerGraph(
                navController,
                viewModel,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }

}
@Composable
fun NavHostPartnerGraph(
    navController: NavHostController,
    viewModel: MainPartnerViewModel,
    modifier: Modifier = Modifier
) {
    val accommodation by viewModel.accommodation.collectAsState()
    val bookings by viewModel.bookings.collectAsState()
    val rating by viewModel.rating.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val booking by viewModel.booking.collectAsState()
    val room by viewModel.room.collectAsState()
    val user by viewModel.user.collectAsState()
    val conversations by viewModel.conversations.collectAsState()
    val conversation by viewModel.conversation.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val notification by viewModel.notification.collectAsState()
    NavHost(navController = navController, startDestination = "home", modifier = modifier){
        composable("home") { DashboardScreen(accommodation, bookings, isLoading, navController, viewModel) }
        composable("add_room") { AddUpdateRoomScreen(navController, accommodation, room, viewModel)
            viewModel.setIsShowBottomBar(false)}
        composable("add_accom") { AddAccomScreen(navController, accommodation, viewModel)
            viewModel.setIsShowBottomBar(false)}
        composable("booking_partner") { BookingListPartner(bookings, viewModel, navController)
            viewModel.setIsShowBottomBar(false)}
        composable("booking_infor_partner") { BookingInforPartner(booking,isLoading, viewModel, navController) }
        composable("chat") { ConversationScreen(conversations, navController, user)
        { conversation -> viewModel.setConversation(conversation) }
            viewModel.setIsShowBottomBar(true)}
        composable("chat_room") { ChatScreen(conversation,user, navController)
        { message -> viewModel.sendMessage(message)}
            viewModel.setIsShowBottomBar(false)}
        composable("notification") { NotificationScreen(notifications, navController,
            { viewModel.deleteAllNotifications()},
            { id -> viewModel.changeNotificationStatus(id)},
            {notification -> viewModel.setNotification(notification)}) }
        composable("notification_detail") { NotificationDetail(notification, navController)
            viewModel.setIsShowBottomBar(false)}
        composable("profile") { ProfileScreen(user, navController,
            {user, context -> viewModel.updateUser(user, context)},
            {}, {viewModel.logout()}, "partner") }
        composable("accom_infor_admin") { HotelDetailsScreen(accommodation, navController, "admin") }
        composable("dashboard_detail") { DashboardDetail(bookings, navController) }
        composable("response_rating") { ResponseReviewScreen(accommodation, viewModel, rating, navController) }
        composable("list_rating") { ListReviewScreen(accommodation, navController, "partner"
        ) { rating -> viewModel.setRating(rating) }
        }
    }
}