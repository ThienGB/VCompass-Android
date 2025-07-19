package com.example.gotravel.ui.module.user.main

import Profile
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.example.gotravel.ui.font.VCompassTheme
import com.example.gotravel.ui.module.general.accomodation.HotelDetailsScreen
import com.example.gotravel.ui.module.user.booking.BookingDetailScreen
import com.example.gotravel.ui.module.user.booking.BookingListActivity
import com.example.gotravel.ui.module.general.chat.ChatScreen
import com.example.gotravel.ui.module.general.login.MainLoginActivity
import com.example.gotravel.ui.module.general.notifications.NotificationDetail
import com.example.gotravel.ui.module.general.review.ListReviewScreen
import com.example.gotravel.ui.module.user.explore.Explore
import com.example.gotravel.ui.module.user.home.HomeScreen
import com.example.gotravel.ui.module.user.room.RoomDetailScreen
import com.example.gotravel.ui.module.user.schedule.Schedule
import com.example.gotravel.ui.module.user.schedule.ScheduleActivity
import com.example.gotravel.ui.module.user.search.SearchAccommodation


class MainUserActivity : ComponentActivity() {
    private lateinit var viewModel: MainUserViewModel
    private lateinit var realmHelper: RealmHelper
    @RequiresApi(Build.VERSION_CODES.O)
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
            VCompassTheme {
                MainScreen(viewModel,this) { intentToBooking() }
            }

        }
    }
    private fun intentToBooking() {
        val intent = Intent(this, BookingListActivity::class.java)
        startActivity(intent)
    }
    private fun intentToSchedule(scheduleId: String) {
        val intent = Intent(this, ScheduleActivity::class.java)
        intent.putExtra("scheduleId", scheduleId)
        startActivity(intent)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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
@Preview(showSystemUi = true)
@Composable
fun CustomBottomBar(
    navController: NavController = rememberNavController(),
    selectedRoute: String = "home"
) {
    val curentTab by remember {mutableStateOf(selectedRoute)}
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(40.dp)
    ) {
        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val items = listOf(
                BottomBarItem("home", painterResource(id = R.drawable.ic_house_solid), painterResource(id = R.drawable.ic_house), "Home"),
                BottomBarItem("search", painterResource(id = R.drawable.ic_search_category_solid), painterResource(id = R.drawable.ic_search_category), "Search"),
                BottomBarItem("new", painterResource(id = R.drawable.ic_add_new), painterResource(id = R.drawable.ic_add_new), "New", "main"),
                BottomBarItem("explore", painterResource(id = R.drawable.ic_compass_solid), painterResource(id = R.drawable.ic_compass), "Explore"),
                BottomBarItem("profile", painterResource(id = R.drawable.ic_profile_shape_solid), painterResource(id = R.drawable.ic_proflie_shape), "Profile")
            )

            items.forEach { item ->
                val isSelected = curentTab == item.route
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(2.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            color = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent
                        )
                        .clickable {
                            navController.navigate(item.route)
                        }
                        .padding(vertical = 5.dp)
                ) {
                    Icon(
                        painter = if (isSelected) item.selectedIcon else item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) Color.Black else if (item.type == "main") colorResource(id = R.color.lightDarkBlue) else Color.Gray,
                        modifier = Modifier.size(if (item.type == "main") 28.dp else 22.dp)
                    )
                }
            }
        }
    }
}

data class BottomBarItem(
    val route: String,
    val icon: Painter,
    val selectedIcon: Painter,
    val label: String,
    val type : String = "normal"
)

@RequiresApi(Build.VERSION_CODES.O)
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
        composable("home") { HomeScreen(navController)
        viewModel.setIsShowBottomBar(true)}
        composable("explore") { Explore(navController)
            viewModel.setIsShowBottomBar(false)}
        composable("chat_room") { ChatScreen(conversation,user, navController)
        { message -> viewModel.sendMessage(message)}
            viewModel.setIsShowBottomBar(false)}
        composable("notification") { }
        composable("notification_detail") { NotificationDetail(notification, navController)
            viewModel.setIsShowBottomBar(false)}
        composable("profile") { Profile()}
        composable("search") { SearchAccommodation(navController, accommodations, searchData, isLoading, viewModel) }
        composable("accom_detail") { HotelDetailsScreen(accommodation, navController) }
        composable("room_detail") { RoomDetailScreen(accommodation,searchData, viewModel, navController) }
        composable("booking_detail") { BookingDetailScreen(accommodation, room, user, navController, viewModel, searchData, intentToBooking) }
        composable("list_rating") { ListReviewScreen(accommodation, navController) }
    }
}