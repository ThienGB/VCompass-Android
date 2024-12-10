package com.example.gotravel.ui.module.user.main

import ProfileScreen
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gotravel.MainApplication
import com.example.gotravel.R
import com.example.gotravel.helper.CommonUtils.getUserFromShareRef
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.helper.SharedPreferencesHelper
import com.example.gotravel.ui.factory.ViewModelFactory
import com.example.gotravel.ui.module.general.accomodation.HotelDetailsScreen
import com.example.gotravel.ui.module.user.booking.BookingDetailScreen
import com.example.gotravel.ui.module.user.booking.BookingListActivity
import com.example.gotravel.ui.module.general.chat.ChatScreen
import com.example.gotravel.ui.module.general.chat.ConversationScreen
import com.example.gotravel.ui.module.general.login.MainLoginActivity
import com.example.gotravel.ui.module.general.notifications.NotificationDetail
import com.example.gotravel.ui.module.general.notifications.NotificationScreen
import com.example.gotravel.ui.module.general.review.ListReviewScreen
import com.example.gotravel.ui.module.user.home.HomeUserScreen
import com.example.gotravel.ui.module.user.room.RoomDetailScreen
import com.example.gotravel.ui.module.user.search.SearchAccommodation


class MainUserActivity : ComponentActivity() {
    private lateinit var viewModel: MainUserViewModel
    private lateinit var realmHelper: RealmHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realmHelper = (application as MainApplication).realmHelper
        val factory = ViewModelFactory(MainUserViewModel::class.java, realmHelper)
        viewModel = ViewModelProvider(this, factory)[MainUserViewModel::class.java]
        val sharedPreferences = getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
        val user = getUserFromShareRef(sharedPreferences)
        if (user.status == "banned"){
            viewModel.logout()
            val intent = Intent(this, MainLoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show()
        }
        viewModel.setUser(user)
        viewModel.fetchHighPriorityData()
        setContent {
            MainScreen(viewModel,this) { intentToBooking() }
        }
    }
    private fun intentToBooking() {
        val intent = Intent(this, BookingListActivity::class.java)
        startActivity(intent)
    }
}

@Composable
fun MainScreen(
    viewModel: MainUserViewModel,
    context: Context,
    intentToBooking: () -> Unit
) {
    val isShowBottomBar by viewModel.isShowBottomBar.collectAsState()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedRoute = currentBackStackEntry?.destination?.route ?: "home"
    Scaffold(
        bottomBar = {
            if (isShowBottomBar)
                CustomBottomBar(navController, selectedRoute )
        }
    ) { paddingValues ->
        NavHostGraph(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues),
            context = context,
            intentToBooking = intentToBooking
        )
    }
}
@Composable
fun CustomBottomBar(
    navController: NavController,
    selectedRoute: String
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(colorResource(id = R.color.primary), Color(0xFF0C47F8), colorResource(id = R.color.primary))
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val items = listOf(
                BottomBarItem("home", Icons.Filled.Home, "Home"),
                BottomBarItem("chat", Icons.Filled.Email, "Chat"),
                BottomBarItem("notification", Icons.Filled.Notifications, "Notification"),
                BottomBarItem("profile", Icons.Filled.AccountCircle, "Profile")
            )

            items.forEach { item ->
                val isSelected = selectedRoute == item.route

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent
                        )
                        .clickable { navController.navigate(item.route) }
                        .padding(vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        fontSize = 12.sp,
                        color = if (isSelected) Color.White else Color.Black
                    )
                }
            }
        }
    }
}

data class BottomBarItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun NavHostGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainUserViewModel,
    context: Context,
    intentToBooking: () -> Unit
)
{
    val accommodations by viewModel.accommodations.collectAsState()
    val accommodation by viewModel.accommodation.collectAsState()
    val conversation by viewModel.conversation.collectAsState()
    val conversations by viewModel.conversations.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val notification by viewModel.notification.collectAsState()
    val room by viewModel.room.collectAsState()
    val user by viewModel.user.collectAsState()
    val searchData by viewModel.searchData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    NavHost(navController = navController, startDestination = "home", modifier = modifier){
        composable("home") { HomeUserScreen(navController, viewModel, context) }
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
            {intentToBooking()}, {viewModel.logout()}) }
        composable("search") { SearchAccommodation(navController, accommodations, searchData, isLoading, viewModel) }
        composable("accom_detail") { HotelDetailsScreen(accommodation, navController) }
        composable("room_detail") { RoomDetailScreen(accommodation,searchData, viewModel, navController) }
        composable("booking_detail") { BookingDetailScreen(accommodation, room, user, navController, viewModel, searchData, intentToBooking) }
        composable("list_rating") { ListReviewScreen(accommodation, navController) }
    }
}