package com.example.gotravel.ui.module.partner.Booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// Màn hình Bookings
class BookingScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookingsScreen(onGuestInfoClick = {})
        }
    }
}

@Composable
fun BookingsScreen(onGuestInfoClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Sort and Filter Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Sort", fontSize = 16.sp)
            Text("Filter", fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // List of bookings
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(2) {
                BookingCard(onGuestInfoClick = onGuestInfoClick)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

// Card hiển thị thông tin booking
@Composable
fun BookingCard(onGuestInfoClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    top = 8.dp,
                    bottom = 8.dp
                ).align(Alignment.End),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Booking on - 23 Jan 2023",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Booking ID: 1393273109", fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "2 Adults - 5 nights", fontSize = 14.sp)
            Text(text = "Single room", fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = "Check-In: ", fontSize = 12.sp, color = Color.Gray)
                Text(text = "23 Mar 2023 10:00 AM", fontSize = 12.sp)
            }
            Row {
                Text(text = "Check-Out: ", fontSize = 12.sp, color = Color.Gray)
                Text(text = "25 Mar 2023 10:00 AM", fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Payment received - ₹ 12000", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onGuestInfoClick) {
                Text(text = "Guest Information")
            }
        }
    }
}


@Preview()
@Composable
fun BookingsScreenPreview() {
    BookingsScreen(onGuestInfoClick = {})
}
