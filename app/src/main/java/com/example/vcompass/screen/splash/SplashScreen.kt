package com.example.vcompass.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.util.clearAllStackAndAdd
import com.example.vcompass.ui.core.icon.CoreImage
import com.example.vcompass.ui.core.icon.CoreImageSource
import com.example.vcompass.resource.MyDimen
import com.vcompass.presentation.enums.StatusOpenApp
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.splash.SplashViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = koinViewModel()
) {
    val statusOpenApp by viewModel.statusOpenApp.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(statusOpenApp) {
        val route = when (statusOpenApp) {
            StatusOpenApp.INTRODUCE -> CoreRoute.Login.route
            StatusOpenApp.LOGGED -> CoreRoute.Home.route
            else -> CoreRoute.Login.route
        }
        navController.clearAllStackAndAdd(route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        CoreImage(
            source = CoreImageSource.Drawable(R.drawable.logo_no_cap),
            modifier = Modifier.size(MyDimen.p100)
        )
    }
}
