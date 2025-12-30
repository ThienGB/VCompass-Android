package com.example.vcompass.screen.introduce

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.util.replace
import com.example.vcompass.resource.CoreTypography
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.splash.IntroduceViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun IntroduceScreen(
    navController: NavController,
    viewModel: IntroduceViewModel = koinViewModel()
) {
    val state by viewModel.stateUI.collectAsState()

    LaunchedEffect(Unit) {
        delay(2000)

    }

    BaseView(
        state = state,
        viewModel = viewModel,
        navController = navController,
    ) {
        TextButton(
            onClick = {
                viewModel.setOpenedApp()
                navController.replace(CoreRoute.Login.route)
            },
        ) {
            Text(
                "Introduce CoreRoute",
                textAlign = TextAlign.Center,
                style = CoreTypography.labelLarge
            )
        }
    }
}