package com.example.vcompass.ui.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vcompass.presentation.util.CoreRoute
import com.example.vcompass.screen.connection.ConnectionScreen
import com.example.vcompass.screen.conversation.ConversationScreen
import com.example.vcompass.screen.feed.HomeFeedScreen
import com.example.vcompass.screen.home.HomeScreen
import com.example.vcompass.screen.introduce.IntroduceScreen
import com.example.vcompass.screen.job.JobLandingScreen
import com.example.vcompass.screen.login.LoginScreen
import com.example.vcompass.screen.splash.SplashScreen
import com.example.vcompass.ui.core.bottombar.CustomBottomBar

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = CoreRoute.Home.route) {
        composable(CoreRoute.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(CoreRoute.Introduce.route) {
            IntroduceScreen(navController = navController)
        }

        composable(CoreRoute.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(CoreRoute.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(CoreRoute.HomeGraph.route) {
            HomeFeedScreen(navController = navController)
        }

        composable(CoreRoute.JobLandingGraph.route) {
            JobLandingScreen(navController = navController)
        }

        composable(CoreRoute.ConversationGraph.route) {
            ConversationScreen(navController = navController)
        }

        composable(CoreRoute.ConnectionGraph.route) {
            ConnectionScreen(navController = navController)
        }
    }
}
