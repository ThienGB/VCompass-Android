package com.example.gotravel.ui.module.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.gotravel.R
import androidx.navigation.NavController
import com.example.gotravel.ui.module.main.login.MainLoginActivity
import com.example.gotravel.ui.module.main.partner.MainPartnerActivity
import com.example.gotravel.ui.module.main.user.MainUserActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun LoginUI(
    navController: NavController,
    authViewModel: AuthViewModel,
    googleSignInClient: GoogleSignInClient
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primary)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                LoginForm(navController, authViewModel, googleSignInClient)
            }

            // Sign Up Button
            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Sign Up", color = Color.Black)
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm(
    navController: NavController,
    authViewModel: AuthViewModel,
    googleSignInClient: GoogleSignInClient
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                val role = authViewModel.checkRoleNavigate();
                if(role == "user")
                {
                    val intent = Intent(context, MainUserActivity::class.java)
                    context.startActivity(intent)
                }
                else if(role == "partner") {
                    val intent = Intent(context, MainPartnerActivity::class.java)
                    context.startActivity(intent)
                }
                if(role.isNullOrEmpty())
                {
                    navController.navigate("select-role")
                }
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState.value as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Booking With Me !", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("Enter your Email") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Email Icon") },
            colors = TextFieldDefaults.textFieldColors(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your Password") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = { Icon(Icons.Default.Settings, contentDescription = "Password Icon") },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (isPasswordVisible) R.drawable.passwordshow else R.drawable.passwordhidden
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )

        TextButton(
            onClick = {  navController.navigate("forget-password")  },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Forgot password?", color = colorResource(R.color.darkGreen))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = { authViewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = authState.value != AuthState.Loading,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.darkBlue))
        ) {
            Text(text = "Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Google Login Button
        Button(
            onClick = {
                val signInIntent = googleSignInClient.signInIntent
                (context as ComponentActivity).startActivityForResult(signInIntent, MainLoginActivity.REQUEST_CODE_SIGN_IN)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.darkBlue))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.google),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Sign in with Google", color = Color.White)
        }
    }
}

