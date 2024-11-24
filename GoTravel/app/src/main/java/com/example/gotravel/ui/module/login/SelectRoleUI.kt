package com.example.gotravel.ui.module.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gotravel.R
import com.example.gotravel.ui.module.main.partner.MainPartnerActivity
import com.example.gotravel.ui.module.main.user.MainUserActivity

@Composable
fun SelectRoleScreen(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()
    var selectedRole by remember { mutableStateOf<String?>("none") }

    // Gộp LaunchedEffect để xử lý cả authState và role
    LaunchedEffect(authState.value, selectedRole) {
        when (authState.value) {
            is AuthState.Authenticated -> {

                when (selectedRole) {
                    "user" -> {
                        val intent = Intent(context, MainPartnerActivity::class.java)
                        context.startActivity(intent)
                    }
                    "partner" -> {
                        val intent = Intent(context, MainPartnerActivity::class.java)
                        context.startActivity(intent)
                    }
                    else -> {
                        Toast.makeText(context, "Select role: ", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.darkBlue)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Your Role",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Nút User
            Button(
                onClick = {
                    authViewModel.setRole("user")
                    selectedRole = "user" // Cập nhật role
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.white))
            ) {
                Text(text = "User", color = colorResource(R.color.black))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nút Partner
            Button(
                onClick = {
                    authViewModel.setRole("partner")
                    selectedRole = "partner" // Cập nhật role
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.white))
            ) {
                Text(text = "Partner", color = colorResource(R.color.black))
            }
        }
    }
}
