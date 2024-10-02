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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.gotravel.R

@Composable
fun RegisterUI() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.darkBlue)),
        contentAlignment = Alignment.Center
    ) {
        // Register Panel
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
                RegisterForm() // Form đăng ký bên trong panel
            }
            Button(
                onClick = { /* Handle register logic here */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.white)
                ),
            ) {
                Text(text = "Register", color = colorResource(R.color.black))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterForm() {
    var fullName by remember { mutableStateOf("") } // Field Tên người dùng
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register With Us!", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Full Name Input (Tên người dùng)
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            placeholder = { Text("Enter your Full Name") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Full Name Icon")
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Username Input
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            placeholder = { Text("Enter your Username") },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "Username Icon")
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Enter your Password") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = { Icon(Icons.Default.Settings, contentDescription = "Password Icon") },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Input
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            placeholder = { Text("Re-enter your Password") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            leadingIcon = { Icon(Icons.Default.Settings, contentDescription = "Confirm Password Icon") },
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Register Button
        Button(
            onClick = { /* Handle register logic here */ },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.darkBlue)
            ),
        ) {
            Text(text = "Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterUI()
}
