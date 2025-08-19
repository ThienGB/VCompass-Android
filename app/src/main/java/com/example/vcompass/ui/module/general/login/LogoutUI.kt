//package com.example.vcompass.ui.module.general.login
//
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.navigation.NavController
//
//@Composable
//fun LogoutUI(navController: NavController, authViewModel: AuthViewModel) {
//    val authState = authViewModel.authState.observeAsState()
//
//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.Unauthenticated -> navController.navigate("login")
//            else -> Unit
//        }
//    }
//
//    TextButton(onClick =  {authViewModel.signout()}) {
//        Text(text = "Sign out")
//    }
//}