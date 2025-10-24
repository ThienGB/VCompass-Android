package com.example.vcompass.ui.core.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.example.vcompass.util.clearAllStackAndGo
import com.vcompass.presentation.event.global.GlobalEvent
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.BaseViewModel

@Composable
fun ObserveGlobalEvents(
    viewModel: BaseViewModel,
    navController: NavController
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel) {
        viewModel.globalEventBus.events
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { event ->
                when (event) {
                    is GlobalEvent.Logout -> {
                        viewModel.globalConfig.clearSessionData()
                        navController.clearAllStackAndGo(CoreRoute.Login.route)
                    }

                    is GlobalEvent.Forbidden -> {
                        viewModel.globalConfig.add403 {
                            viewModel.navigateToLoginScreen()
                        }
                    }

                    is GlobalEvent.ShowDialog -> {
                        //TODO implement after
                    }

                    is GlobalEvent.ForceUpdate -> {
                        //TODO implement after
                    }
                }
            }
    }
}
