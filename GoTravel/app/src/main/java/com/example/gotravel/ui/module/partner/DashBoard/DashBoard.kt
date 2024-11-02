package com.example.gotravel.ui.module.partner.DashBoard


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview



class DashBoard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DashboardScreen()
        }
    }
}

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light gray background
            .padding(10.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Take up available space
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp) // Padding for the column
        ) {
            // Display hotel info card at the top
            item {
                HotelInfoCard()
                Spacer(modifier = Modifier.height(16.dp)) // Spacer for spacing
            }

            // Display booking stats
            item {
                BookingStats()
                Spacer(modifier = Modifier.height(16.dp)) // Spacer for spacing
            }

            // Display recent bookings
            item {
                RecentBookings()
                Spacer(modifier = Modifier.height(16.dp)) // Spacer for spacing
            }

            // Display rooms listed
            item {
                RoomsListed() // Pass room data to the function
                Spacer(modifier = Modifier.height(8.dp)) // Spacer for spacing between room items
            }
        }
    }
}

@Composable
fun HotelInfoCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Thay thế bằng hình ảnh khách sạn
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Gray, RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Raddison Hotel", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = "Noida", color = Color.Gray, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun BookingStats() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        StatCard(
            title = "PAYMENTS RECEIVED",
            amount = "₹ 12,426",
            percentageChange = "+36%",
            isPositive = true
        )
        StatCard(
            title = "TOTAL BOOKINGS",
            amount = "5",
            percentageChange = "+14%",
            isPositive = false
        )
    }
}

@Composable
fun StatCard(title: String, amount: String, percentageChange: String, isPositive: Boolean) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = amount, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isPositive) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = if (isPositive) Color.Green else Color.Red
                )
                Text(
                    text = percentageChange,
                    color = if (isPositive) Color.Green else Color.Red,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun RecentBookings() {
    Column {
        Text(text = "Recent Bookings", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        BookingCard()
    }
}

@Composable
fun BookingCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Booking on - 23 Jan 2023",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(text = "2 Adults - 5 nights", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Single room", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Check-In", fontWeight = FontWeight.Bold)
                    Text(text = "23 Mar 2023", color = Color.Gray)
                    Text(text = "10:00 AM", color = Color.Gray)
                }
                Column {
                    Text(text = "Check-Out", fontWeight = FontWeight.Bold)
                    Text(text = "25 Mar 2023", color = Color.Gray)
                    Text(text = "10:00 AM", color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Guest Information")
            }
        }
    }
}

@Composable
fun RoomsListed() {
    Column() {
        Text(
            text = "Rooms Listed",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Sử dụng LazyRow để tạo scroll ngang
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),  // Khoảng cách giữa các item
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp)  // Khoảng cách hai đầu
        ) {
            items(6) {  // Giả sử có 2 phòng, có thể thay đổi số lượng này
                RoomCard()
            }
        }
    }
}

@Composable
fun RoomCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Deluxe King Room", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Non-Refundable", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Price for 1 Adult", fontSize = 12.sp, color = Color.Gray)
            Text(text = "₹ 3,493", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Green)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.End)) {
                Text(text = "Edit")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HotelDashboardPreview() {
    DashboardScreen()
}
