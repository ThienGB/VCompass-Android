package com.example.vcompass.ui.module.admin.main

import ProfileScreen
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vcompass.MainApplication
import com.example.vcompass.helper.CommonUtils.getUserFromShareRef
import com.example.vcompass.helper.RealmHelper
import com.example.vcompass.helper.SharedPreferencesHelper
import com.example.vcompass.ui.factory.ViewModelFactory
import com.example.vcompass.ui.module.general.accomodation.HotelDetailsScreen
import com.example.vcompass.ui.module.admin.home.HomeAdmin
import com.example.vcompass.ui.module.admin.accommodation.AccomList
import com.example.vcompass.ui.module.admin.accommodation.AccomRegisterList
import com.example.vcompass.ui.module.admin.user.ListUser
import com.example.vcompass.ui.module.admin.user.UserInfor
import com.example.vcompass.ui.module.general.chat.ChatScreen
import com.example.vcompass.ui.module.general.chat.ConversationScreen
import com.example.vcompass.ui.module.user.main.CustomBottomBar
import com.example.vcompass.ui.module.general.notifications.NotificationDetail
import com.example.vcompass.ui.module.general.notifications.NotificationScreen
import com.example.vcompass.ui.module.general.review.ListReviewScreen


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
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedRoute = currentBackStackEntry?.destination?.route ?: "home"
    Scaffold(
        bottomBar = {
            if(isShowBottomBar)
                CustomBottomBar(navController, selectedRoute)
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
fun NavHostAdminGraph(
    navController: NavHostController,
    viewModel: MainAdminViewModel,
    modifier: Modifier = Modifier
) {
    val accommodations by viewModel.accommodations.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val accommodation  by viewModel.accommodation.collectAsState()
    val users by viewModel.users.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val user by viewModel.user.collectAsState()
    val conversation by viewModel.conversation.collectAsState()
    val conversations by viewModel.conversations.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val notification by viewModel.notification.collectAsState()
    NavHost(navController = navController, startDestination = "home", modifier = modifier){
        composable("home") { HomeAdmin(user ,accommodations, users, isLoading, navController, viewModel) }
        composable("list_user") { ListUser(users, navController, viewModel, isLoading, "user") }
        composable("list_partner") { ListUser(users, navController, viewModel, isLoading, "partner") }
        composable("user_infor") { UserInfor(currentUser, navController,
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
            {}, {viewModel.logout()},  "admin") }
        composable("list_rating") { ListReviewScreen(accommodation, navController) }
    }
}