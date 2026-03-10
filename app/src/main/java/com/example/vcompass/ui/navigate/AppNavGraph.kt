package com.example.vcompass.ui.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.vcompass.screen.accommodation.AccommodationDetailScreen
import com.example.vcompass.screen.explore.ExploreScreen
import com.example.vcompass.screen.explore.search.ExploreSearchScreen
import com.example.vcompass.screen.home.HomeScreen
import com.example.vcompass.screen.introduce.IntroduceScreen
import com.example.vcompass.screen.login.LoginScreen
import com.example.vcompass.screen.schedule.create_schedule.CreateScheduleScreen
import com.example.vcompass.screen.schedule.schedule_detail.ScheduleScreen
import com.example.vcompass.screen.search.MapSearchScreen
import com.example.vcompass.screen.splash.SplashScreen
import com.example.vcompass.util.ScreenContext
import com.example.vcompass.util.getArg
import com.vcompass.presentation.util.CoreRoute
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavGraph() {
    val navController = ScreenContext.navController
    NavHost(
        navController,
        startDestination = CoreRoute.Splash.route,
        route = CoreRoute.MainGraph.route,
        enterTransition = { defaultEnterTransition(initialState, targetState) },
        exitTransition = { defaultExitTransition(initialState, targetState) },
        popEnterTransition = { defaultPopEnterTransition(initialState, targetState) },
        popExitTransition = { defaultPopExitTransition(initialState, targetState) }
    ) {
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

        composable(CoreRoute.Explore.route) {
            ExploreScreen(navController = navController)
        }

        composable(CoreRoute.ExploreSearch.route) {
            ExploreSearchScreen(navController = navController)
        }

        composable(CoreRoute.MapSearch.route) {
            MapSearchScreen()
        }
        composable(CoreRoute.Schedule.route) {
            val scheduleId = navController.getArg<String>(NavigateKeyArg.SCHEDULE_ID)
            val params = parametersOf(scheduleId)
            ScheduleScreen(params)
        }
        composable(CoreRoute.AccommodationDetail.route) {
            AccommodationDetailScreen()
        }
        composable(CoreRoute.CreateSchedule.route) {
            CreateScheduleScreen()
        }
    }
}
