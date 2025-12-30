package com.example.vcompass.screen.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.enum.LoginActionType
import com.example.vcompass.ui.core.general.BaseView
import com.example.vcompass.util.backAndNavigate
import com.example.vcompass.ui.core.icon.CoreIcon
import com.example.vcompass.ui.core.space.SpaceHeight
import com.example.vcompass.resource.MyColor
import com.example.vcompass.resource.MyDimen
import com.vcompass.presentation.util.CoreRoute
import com.vcompass.presentation.viewmodel.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.stateUI.collectAsState()
    var loginAction by remember { mutableStateOf(LoginActionType.LOGIN) }
    val isLogin by remember { derivedStateOf { loginAction == LoginActionType.LOGIN } }

    val topPadding by animateDpAsState(
        targetValue = if (isLogin) MyDimen.p250 else MyDimen.zero,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "topPaddingAnim"
    )
    LaunchedEffect(Unit) {
        viewModel.registerSuccess.collect {
            if (it)
                navController.backAndNavigate(
                    targetBack = CoreRoute.Login.route,
                    navigate = CoreRoute.Home.route
                )
        }
    }

    BaseView(
        state = state,
        viewModel = viewModel,
        navController = navController,
        statusBarPadding = false
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            TravelLoginAnimation()
            Column(
                modifier = Modifier
                    .zIndex(1f)
                    .padding(top = topPadding)
                    .clip(RoundedCornerShape(topStart = MyDimen.p32, topEnd = MyDimen.p32))
                    .background(MyColor.White)
                    .padding(horizontal = MyDimen.p16),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpaceHeight()
                if (!isLogin) {
                    SpaceHeight()
                }
                CoreIcon(
                    resDrawable = R.drawable.logo_vcompass,
                    iconModifier = Modifier
                        .height(MyDimen.p48)
                        .width(MyDimen.p250),
                    tintColor = Color.Unspecified
                )
                SpaceHeight()
                AnimatedContent(targetState = isLogin) { target ->
                    if (target) {
                        LoginForm(
                            navController = navController,
                            onRegisterClick = { loginAction = LoginActionType.REGISTER },
                            onForgotClick = { loginAction = LoginActionType.FORGOT }
                        )
                    } else {
                        if (loginAction == LoginActionType.REGISTER)
                            RegisterForm(
                                navController = navController,
                                onBackToLogin = { loginAction = LoginActionType.LOGIN },
                            )
                        else if (loginAction == LoginActionType.FORGOT)
                            ForgotForm(
                                navController = navController,
                                onBackToLogin = { loginAction = LoginActionType.LOGIN }
                            )
                    }
                }
            }
        }
    }
}
