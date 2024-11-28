package com.example.gotravel.ui.module.admin.main

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
import com.example.gotravel.data.model.UserAccount
import com.example.gotravel.helper.CommonUtils.getUserFromShareRef
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.helper.SharedPreferencesHelper
import com.example.gotravel.ui.factory.ViewModelFactory
import com.example.gotravel.ui.module.accomodation.HotelDetailsScreen
import com.example.gotravel.ui.module.admin.accommodation.AccomList
import com.example.gotravel.ui.module.admin.HomeAdmin
import com.example.gotravel.ui.module.admin.accommodation.AccomRegisterList
import com.example.gotravel.ui.module.admin.user.ListUser
import com.example.gotravel.ui.module.admin.user.UserInfor
import com.example.gotravel.ui.module.chat.ChatComponentScreen
import com.example.gotravel.ui.module.home.user.NotificationScreen
import com.example.gotravel.ui.module.home.user.ProfileScreen
import com.example.gotravel.ui.module.review.ListReviewScreen


class MainAdminActivity : ComponentActivity() {
    private lateinit var viewModel: MainAdminViewModel
    private lateinit var realmHelper: RealmHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realmHelper = (application as MainApplication).realmHelper
        val factory = ViewModelFactory(MainAdminViewModel::class.java, realmHelper)
        viewModel = ViewModelProvider(this, factory)[MainAdminViewModel::class.java]
        val sharedPreferences = getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
        val user = getUserFromShareRef(sharedPreferences)
        viewModel.setUser(user)
        viewModel.fetchData()
        setContent {
            MainAdminScreen(viewModel)
        }
    }
}

@Composable
fun MainAdminScreen(
    viewModel: MainAdminViewModel
) {
    val isShowBottomBar by viewModel.isShowBottomBar.collectAsState()
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            if(isShowBottomBar)
                CustomBottomBar(navController)
        }
    ) { paddingValues ->
        NavHostAdminGraph(
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
            onClick = { navController.navigate("home_admin") }
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
fun NavHostAdminGraph(
    navController: NavHostController,
    viewModel: MainAdminViewModel,
    modifier: Modifier = Modifier
) {
    val accommodations by viewModel.accommodations.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val accommodation  by viewModel.accommodation.collectAsState()
    val users by viewModel.users.collectAsState()
    val user by viewModel.currentUser.collectAsState()
    NavHost(navController = navController, startDestination = "home_admin", modifier = modifier){
        composable("home_admin") { HomeAdmin(accommodations, users, isLoading, navController, viewModel) }
        composable("list_user") { ListUser(users, navController, viewModel, isLoading, "user") }
        composable("list_partner") { ListUser(users, navController, viewModel, isLoading, "partner") }
        composable("user_infor") { UserInfor(user, navController,
            { viewModel.handleBanUser("banned") },
            { viewModel.handleBanUser("Active") }) }
        composable("list_accom") { AccomList(accommodations, navController, viewModel) }
        composable("list_accom_register") { AccomRegisterList(accommodations, navController, viewModel) }
        composable("accept_register") { HotelDetailsScreen(
            accommodation, navController, "accept",
            { viewModel.handleConfirmAccom("cancel") },
            { viewModel.handleConfirmAccom("accept") }) }
        composable("accom_infor_admin") { HotelDetailsScreen(accommodation, navController, "admin") }
        composable("list_rating") { ListReviewScreen(accommodation, navController) }
        composable("chat") { ChatComponentScreen() }
        composable("notification") { NotificationScreen() }
        composable("profile") { ProfileScreen() }
    }
}