package com.example.gotravel.ui.module.main.partner

import android.content.Context
import android.os.Bundle
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
import androidx.navigation.compose.rememberNavController
import com.example.gotravel.MainApplication
import com.example.gotravel.helper.CommonUtils.getUserFromShareRef
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.helper.SharedPreferencesHelper
import com.example.gotravel.ui.factory.ViewModelFactory
import com.example.gotravel.ui.module.chat.ChatComponentScreen
import com.example.gotravel.ui.module.home.user.NotificationScreen
import com.example.gotravel.ui.module.home.user.ProfileScreen
import com.example.gotravel.ui.module.partner.Booking.BookingInforPartner
import com.example.gotravel.ui.module.partner.Booking.BookingListPartner
import com.example.gotravel.ui.module.partner.DashBoard.DashboardScreen
import com.example.gotravel.ui.module.partner.Rooms.AddUpdateRoomScreen
import com.example.gotravel.ui.module.partner.accommodation.AddAccomScreen


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
        viewModel.setUser(user)
        viewModel.fetchData()
        setContent {
            MainPartnerScreen(viewModel)
        }
    }
}

@Composable
fun MainPartnerScreen(
    viewModel: MainPartnerViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavHostPartnerGraph(
            navController,
            viewModel,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
@Composable
fun CustomBottomBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home_partner") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Email, contentDescription = "Chat") },
            label = { Text("Chat") },
            selected = false,
            onClick = { navController.navigate("chat") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notification") },
            label = { Text("Notification") },
            selected = false,
            onClick = { navController.navigate("notification") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("profile") }
        )
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
    val isLoading by viewModel.isLoading.collectAsState()
    val booking by viewModel.booking.collectAsState()
    val room by viewModel.room.collectAsState()
    NavHost(navController = navController, startDestination = "home_partner", modifier = modifier){
        composable("home_partner") { DashboardScreen(accommodation, bookings, isLoading, navController, viewModel) }
        composable("add_room") { AddUpdateRoomScreen(navController, accommodation, room, viewModel) }
        composable("add_accom") { AddAccomScreen(navController, accommodation, viewModel) }
        composable("booking_partner") { BookingListPartner(bookings, viewModel, navController) }
        composable("booking_infor_partner") { BookingInforPartner(booking,isLoading, viewModel, navController) }
        composable("chat") { ChatComponentScreen() }
        composable("notification") { NotificationScreen() }
        composable("profile") { ProfileScreen() }
    }
}