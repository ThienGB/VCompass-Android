package com.example.gotravel.ui.module.partner.Booking

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class GuestInfo : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuestInfoScreen(
                name = "John Doe",
                email = "johndoe@example.com",
                phoneNumber = "+123456789",
                roomType = "Single Room",
                checkInDate = "23 Mar 2023",
                checkOutDate = "25 Mar 2023",
                onBackClick = {}
            )
        }
    }

}
// GuestInfo Screen
@Composable
fun GuestInfoScreen(
    name: String,
    email: String,
    phoneNumber: String,
    roomType: String,
    checkInDate: String,
    checkOutDate: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Guest Information", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Display guest information
        Text("Name: $name", fontSize = 16.sp)
        Text("Email: $email", fontSize = 16.sp)
        Text("Phone: $phoneNumber", fontSize = 16.sp, color = Color.Blue)
        Spacer(modifier = Modifier.height(8.dp))

        Text("Room: $roomType", fontSize = 16.sp)
        Text("Check-In: $checkInDate", fontSize = 16.sp)
        Text("Check-Out: $checkOutDate", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Button to call phone number
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                context.startActivity(intent)
            }
        ) {
            Text("Call $phoneNumber")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Back button
        Button(onClick = onBackClick) {
            Text("Back to Booking")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGuestInfoScreen() {
    GuestInfoScreen(
        name = "John Doe",
        email = "johndoe@example.com",
        phoneNumber = "+123456789",
        roomType = "Single Room",
        checkInDate = "23 Mar 2023",
        checkOutDate = "25 Mar 2023",
        onBackClick = {}
    )
}
