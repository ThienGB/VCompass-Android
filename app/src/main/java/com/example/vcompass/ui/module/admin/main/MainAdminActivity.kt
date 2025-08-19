//package com.example.vcompass.ui.module.admin.main
//
//import android.content.Context
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.vcompass.helper.CommonUtils.getUserFromShareRef
//import com.example.vcompass.helper.SharedPreferencesHelper
//import com.example.vcompass.ui.module.user.main.CustomBottomBar
//
//
//class MainAdminActivity : ComponentActivity() {
//    private lateinit var viewModel: MainAdminViewModel
//   // private lateinit var realmHelper: RealmHelper
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val sharedPreferences = getSharedPreferences(SharedPreferencesHelper.SHARED_PREFS, Context.MODE_PRIVATE)
//        val user = getUserFromShareRef(sharedPreferences)
//        viewModel.setUser(user)
//        viewModel.fetchData()
//        setContent {
//            MainAdminScreen(viewModel)
//        }
//    }
//}
//
//@Composable
//fun MainAdminScreen(
//    viewModel: MainAdminViewModel
//) {
//    val isShowBottomBar by viewModel.isShowBottomBar.collectAsState()
//    val navController = rememberNavController()
//    val currentBackStackEntry by navController.currentBackStackEntryAsState()
//    val selectedRoute = currentBackStackEntry?.destination?.route ?: "home"
//    Scaffold(
//        bottomBar = {
//            if(isShowBottomBar)
//                CustomBottomBar(navController, selectedRoute)
//        }
//    ) { paddingValues ->
//        NavHostAdminGraph(
//            navController,
//            viewModel,
//            modifier = Modifier.padding(paddingValues)
//        )
//    }
//}
//@Composable
//fun NavHostAdminGraph(
//    navController: NavHostController,
//    viewModel: MainAdminViewModel,
//    modifier: Modifier = Modifier
//) {
//
//    NavHost(navController = navController, startDestination = "home", modifier = modifier){
//    }
//}