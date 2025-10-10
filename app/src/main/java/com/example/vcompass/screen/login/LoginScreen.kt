package com.example.vcompass.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.ui.core.ScreenNormal
import com.example.vcompass.util.back
import com.example.vcompass.util.goWithState
import com.vcompass.core.resource.MyDimen
import com.vcompass.core.typography.CoreTypography
import com.vcompass.presentation.viewmodel.login.LoginViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.stateUI.collectAsState()
    var email by remember { mutableStateOf("zoro") }
    var password by remember { mutableStateOf("Password1!") }

    LaunchedEffect(Unit) {
        viewModel.navigate.collect {
            navController.goWithState(it)
        }
    }

    ScreenNormal(
        state = state,
        textRetry = stringResource(R.string.app_name),
        onBackPress = { navController.back() },
        viewModel = viewModel,
        navController = navController,
        onRetry = { viewModel.login(email, password) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MyDimen.p16),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = email,
                onValueChange = { data ->
                    email = data
                },
                textStyle = CoreTypography.labelLarge,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(MyDimen.p8))

            TextField(
                value = password,
                onValueChange = { data ->
                    password = data
                },
                textStyle = CoreTypography.labelLarge,
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(MyDimen.p16))

            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Đăng nhập")
            }
        }
    }
}
