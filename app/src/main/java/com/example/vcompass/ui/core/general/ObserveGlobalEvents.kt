package com.example.vcompass.ui.core.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.example.vcompass.util.clearAllStackAndAdd
import com.vcompass.presentation.event.global.GlobalEvent
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.BaseViewModel

@Composable
fun ObserveGlobalEvents(
    viewModel: BaseViewModel,
    navController: NavController? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel) {
        viewModel.globalEventBus.events
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .collect { event ->
                when (event) {
                    is GlobalEvent.Logout -> {
                        viewModel.handleExpiredToken()
                        navController?.clearAllStackAndAdd(CoreRoute.Login.route)
                    }

                    is GlobalEvent.ForceUpdate -> {
                        viewModel.onForceUpdate()
                    }

                    is GlobalEvent.Idle -> {
                        viewModel.resetStateLocal()
                    }

                    else -> {}
                }
            }
    }
}
