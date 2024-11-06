package com.example.gotravel.ui.module.main.user

import android.content.Context
import android.content.Intent
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
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.helper.RealmHelper
import com.example.gotravel.ui.factory.ViewModelFactory
import com.example.gotravel.ui.module.accomodation.AccomodationDetail
import com.example.gotravel.ui.module.chat.ChatComponentScreen
import com.example.gotravel.ui.module.home.user.HomeUserScreen
import com.example.gotravel.ui.module.home.user.NotificationScreen
import com.example.gotravel.ui.module.home.user.ProfileScreen
import com.example.gotravel.ui.module.search.SearchAccommodation


class MainUserActivity : ComponentActivity() {
    private lateinit var viewModel: MainUserViewModel
    private lateinit var realmHelper: RealmHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realmHelper = (application as MainApplication).realmHelper
        val factory = ViewModelFactory(MainUserViewModel::class.java, realmHelper)
        viewModel = ViewModelProvider(this, factory)[MainUserViewModel::class.java]
        setContent {
            MainScreen(viewModel, this){ accommodationId -> intentToAccomDetail(accommodationId) }
        }
    }
    private fun intentToAccomDetail(accommodationId: String) {
        val intent = Intent(this, AccomodationDetail::class.java)
        intent.putExtra("accommodationId", accommodationId)
        startActivity(intent)
    }
}

@Composable
fun MainScreen(
    viewModel: MainUserViewModel,
    context: Context,
    intentToAccomDetail: (String) -> Unit
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            CustomBottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavHostGraph(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(paddingValues),
            context = context,
            intentToAccomDetail = intentToAccomDetail
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
            onClick = { navController.navigate("home") }
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
fun NavHostGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainUserViewModel,
    context: Context,
    intentToAccomDetail: (String) -> Unit
)
{
    val accommodations by viewModel.accommodations.collectAsState()
    val searchData by viewModel.searchData.collectAsState()
    NavHost(navController = navController, startDestination = "home", modifier = modifier){
        composable("home") { HomeUserScreen(navController, viewModel, context) }
        composable("chat") { ChatComponentScreen() }
        composable("notification") { NotificationScreen() }
        composable("profile") { ProfileScreen() }
        composable("search") { SearchAccommodation(navController, accommodations, searchData, intentToAccomDetail) }
    }
}