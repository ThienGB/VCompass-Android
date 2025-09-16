package com.example.vcompass.ui.navigate

import androidx.compose.runtime.Composable
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

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = CoreRoute.Splash.route) {
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
