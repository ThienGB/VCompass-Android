//package com.example.vcompass.ui.module.general.login
//
//import android.content.Intent
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalSoftwareKeyboardController
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.vcompass.ui.module.partner.main.MainPartnerActivity
//import com.example.vcompass.ui.module.user.main.MainUserActivity
//import com.example.vcompass.R
//
//@Composable
//fun RegisterUI(navController: NavController, authViewModel: AuthViewModel) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        //Background
//        Image(
//            painter = painterResource(id = R.drawable.img_beach),
//            contentDescription = "Background",
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//
//        // Register Panel
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Card(
//                shape = RoundedCornerShape(16.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                elevation = CardDefaults.cardElevation(8.dp)
//            ) {
//                RegisterForm(navController, authViewModel) // Form đăng ký bên trong panel
//            }
//            Button(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = colorResource(R.color.white)
//                ),
//            ) {
//                Text(text = "Quay lại trang đăng nhập", color = colorResource(R.color.black))
//            }
//        }
//    }
//}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RegisterForm(navController: NavController, authViewModel: AuthViewModel) {
//    var fullName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var phone by remember { mutableStateOf("") }
//    var confirmPassword by remember { mutableStateOf("") }
//    var isPasswordVisible by remember { mutableStateOf(false) }
//    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
//    var selectedRole by remember { mutableStateOf("user") }
//
//    val authState = authViewModel.authState.observeAsState()
//    val context = LocalContext.current
//
//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.Authenticated -> {
//                val role = authViewModel.checkRoleNavigate()
//
//                if(role.isNullOrEmpty())
//                {
//                    navController.navigate("select-role")
//                }
//
//                if(role == "user")
//                {
//                    val intent = Intent(context, MainUserActivity::class.java)
//                    context.startActivity(intent)
//                }
//                else if(role == "partner")
//                {
//                    val intent = Intent(context, MainPartnerActivity::class.java)
//                    context.startActivity(intent)
//                }
//            }
//            is AuthState.Error -> {
//                Toast.makeText(
//                    context,
//                    (authState.value as AuthState.Error).message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            else -> Unit
//        }
//    }
//
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Đăng ký", style = MaterialTheme.typography.headlineLarge)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Full Name Input
//        OutlinedTextField(
//            value = fullName,
//            onValueChange = { fullName = it },
//            label = { Text("Họ và Tên") },
//            placeholder = { Text("Nhập họ và tên") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Email Input
//        OutlinedTextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") },
//            placeholder = { Text("Nhập email") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = phone,
//            onValueChange = { input ->
//                if (input.length <= 11 && input.all { it.isDigit() }) {
//                    phone = input
//                }
//            },
//            label = { Text("Số Điện thoai") },
//            placeholder = { Text("Nhập số điện thoai") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Password Input
//        OutlinedTextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Mật khẩu") },
//            placeholder = { Text("Nhập mật khẩu") },
//            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            trailingIcon = {
//                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
//                    Icon(
//                        painter = painterResource(
//                            id = if (isPasswordVisible) R.drawable.passwordshow else R.drawable.passwordhidden
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier.size(18.dp)
//                    )
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Confirm Password Input
//        OutlinedTextField(
//            value = confirmPassword,
//            onValueChange = { confirmPassword = it },
//            label = { Text("Nhập lại mật kẩu") },
//            placeholder = { Text("Vui lòng nhập lại mật khẩu") },
//            visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            trailingIcon = {
//                IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
//                    Icon(
//                        painter = painterResource(
//                            id = if (isPasswordVisible) R.drawable.passwordshow else R.drawable.passwordhidden
//                        ),
//                        contentDescription = null,
//                        modifier = Modifier.size(18.dp)
//                    )
//                }
//            }
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Register Button
//        Button(
//            onClick = {
//                if (password != confirmPassword) {
//                    Toast.makeText(
//                        context,
//                        "Mật khẩu không khớp !",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    authViewModel.signup(email, password, fullName, phone)
//                }
//            },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = colorResource(R.color.primary)
//            ),
//        ) {
//            Text(text = "Đăng ký", color = colorResource(R.color.white))
//        }
//    }
//}