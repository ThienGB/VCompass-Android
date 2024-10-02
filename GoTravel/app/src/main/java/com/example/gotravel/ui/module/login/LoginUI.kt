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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.gotravel.R

@Composable
fun LoginUI() {
    Box(
        modifier = Modifier
            .fillMaxSize().background(colorResource(R.color.darkBlue)),
        contentAlignment = Alignment.Center)
    {
        // Login Panel
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
                LoginForm() // Form đăng nhập bên trong panel
            }
            Button(
                onClick = { /* Handle login logic here */ },
                modifier = Modifier
                    .fillMaxWidth().padding(16.dp),
                colors =  ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.white)
                ),
            ) {
                Text(text = "Sign Up",color = colorResource((R.color.black)))
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginForm() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Booking With Me !", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

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
                imeAction = ImeAction.Done,
                keyboardType =  KeyboardType.Password),
            leadingIcon = {Icon(Icons.Default.Settings,  contentDescription = "Password Icon")},
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide();
                }
            ),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        TextButton(onClick = {} ,
            modifier = Modifier.align(Alignment.End)

        ) {
            Text(text = "Forgot password ?", color = colorResource((R.color.darkGreen)))
        }

        // Login Button
        Button(
            onClick = { /* Handle login logic here */ },
            modifier = Modifier
                .fillMaxWidth(),
            colors =  ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.darkBlue)
            ),
        ) {
            Text(text = "Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "or sign in with ABV")
        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround // Align icons horizontally
        ) {
            IconButton(
                onClick = { /* Handle Google login logic here */ },
                modifier = Modifier.size(35.dp) // Button size
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Login with Google",
                    modifier = Modifier.size(56.dp), // Icon size
                    tint = Color.Unspecified // Keep original color of the icon
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreenWithBackground() {
    LoginUI()
}
