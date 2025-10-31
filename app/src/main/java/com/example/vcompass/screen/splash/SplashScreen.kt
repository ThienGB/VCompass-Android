package com.example.vcompass.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.util.replace
import com.vcompass.core.resource.MyDimen
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.splash.SplashViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = koinViewModel()
) {
    val logged = viewModel.isLogged.collectAsState()
    val isOpenedApp = viewModel.isOpenedApp.collectAsState()

    LaunchedEffect(logged, isOpenedApp) {
        val route = when {
            isOpenedApp.value ->
                if (logged.value) CoreRoute.Home.route
                else CoreRoute.Login.route

            else -> CoreRoute.Introduce.route
        }
        delay(2000)
        navController.replace(route)
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_share),
            contentDescription = "",
            modifier = Modifier.size(MyDimen.p100)
        )
    }
}
