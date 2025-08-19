//package com.example.vcompass.ui.module.user.main
//
//import Profile
//import android.content.Context
//import android.content.Intent
//import android.os.Build
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.core.view.WindowCompat
//import androidx.core.view.WindowInsetsControllerCompat
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.vcompass.MainApplication
//import com.example.vcompass.R
//import com.example.vcompass.helper.CommonUtils.getUserFromShareRef
//import com.example.vcompass.helper.RealmHelper
//import com.example.vcompass.helper.SharedPreferencesHelper
//import com.example.vcompass.ui.factory.ViewModelFactory
//import com.example.vcompass.ui.font.VCompassTheme
//import com.example.vcompass.ui.module.base.BaseLayout
//import com.example.vcompass.ui.module.general.accomodation.HotelDetailsScreen
//import com.example.vcompass.ui.module.general.chat.ChatScreen
//import com.example.vcompass.ui.module.general.login.MainLoginActivity
//import com.example.vcompass.ui.module.general.notifications.NotificationDetail
//import com.example.vcompass.ui.module.general.review.ListReviewScreen
//import com.example.vcompass.ui.module.user.explore.Explore
//import com.example.vcompass.ui.module.user.home.HomeScreen
//import com.example.vcompass.ui.module.user.room.RoomDetailScreen
//import com.example.vcompass.ui.module.user.search.SearchAccommodation
//
//
//class MainUserActivity : ComponentActivity() {
//    private lateinit var viewModel: MainUserViewModel
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//      val sharedPreferences = getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
//        val user = getUserFromShareRef(sharedPreferences)
//        viewModel.setUser(user)
//        viewModel.fetchHighPriorityData()
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        setContent {
//            VCompassTheme {
//                MainScreen(viewModel)
//            }
//        }
//    }
//}
//
//@Composable
//fun MainScreen(
//    viewModel: MainUserViewModel,
//) {
//    val isShowBottomBar by viewModel.isShowBottomBar.collectAsState()
//    val navController = rememberNavController()
//    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//    val selectedRoute = currentBackStackEntry?.destination?.route ?: "home"
//
//    BaseLayout(
//        bottomBar = {
//            if (isShowBottomBar) {
//                CustomBottomBar(
//                    navController = navController,
//                    selectedRoute = selectedRoute
//                )
//            }
//        }
//    ) { innerPadding ->
//        NavHostGraph(
//            navController = navController,
//            viewModel = viewModel,
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding),
//        )
//    }
//}
//@Preview(showSystemUi = true)
//@Composable
//fun CustomBottomBar(
//    navController: NavController = rememberNavController(),
//    selectedRoute: String = "home"
//) {
//    val curentTab by remember {mutableStateOf(selectedRoute)}
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Color.White)
//            .height(40.dp)
//    ) {
//        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
//        Row(
//            modifier = Modifier
//                .fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            val items = listOf(
//                BottomBarItem("home", painterResource(id = R.drawable.ic_house_solid), painterResource(id = R.drawable.ic_house), "Home"),
//                BottomBarItem("search", painterResource(id = R.drawable.ic_search_category_solid), painterResource(id = R.drawable.ic_search_category), "Search"),
//                BottomBarItem("new", painterResource(id = R.drawable.ic_add_new), painterResource(id = R.drawable.ic_add_new), "New", "main"),
//                BottomBarItem("explore", painterResource(id = R.drawable.ic_compass_solid), painterResource(id = R.drawable.ic_compass), "Explore"),
//                BottomBarItem("profile", painterResource(id = R.drawable.ic_profile_shape_solid), painterResource(id = R.drawable.ic_proflie_shape), "Profile")
//            )
//
//            items.forEach { item ->
//                val isSelected = curentTab == item.route
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .padding(2.dp)
//                        .weight(1f)
//                        .clip(RoundedCornerShape(6.dp))
//                        .background(
//                            color = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent
//                        )
//                        .clickable {
//                            navController.navigate(item.route)
//                        }
//                        .padding(vertical = 5.dp)
//                ) {
//                    Icon(
//                        painter = if (isSelected) item.selectedIcon else item.icon,
//                        contentDescription = item.label,
//                        tint = if (isSelected) Color.Black else if (item.type == "main") colorResource(id = R.color.lightDarkBlue) else Color.Gray,
//                        modifier = Modifier.size(if (item.type == "main") 28.dp else 22.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//data class BottomBarItem(
//    val route: String,
//    val icon: Painter,
//    val selectedIcon: Painter,
//    val label: String,
//    val type : String = "normal"
//)
//
//@Composable
//fun NavHostGraph(
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    viewModel: MainUserViewModel,
//)
//{
//    NavHost(navController = navController, startDestination = "home", modifier = modifier){
//        composable("home") { HomeScreen()
//        viewModel.setIsShowBottomBar(true)}
//        composable("explore") { Explore(navController)
//            viewModel.setIsShowBottomBar(false)}
//    }
//}