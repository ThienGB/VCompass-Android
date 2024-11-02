package com.example.gotravel.ui.module.partner.Rooms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun RoomInfoScreen(navController: NavController, room: Room) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Room Image
        Image(
            painter = rememberImagePainter( room.image.url), // Replace with an appropriate image loading mechanism
            contentDescription = "Room Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        // Room Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(10.dp)

        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Room Number: ${room.roomNumber}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(text = "Room Type: ${room.roomType}", fontSize = 18.sp, color = Color.Gray)

                // Price formatted with currency
                Text(
                    text = "Price: ₹${room.price}",
                    fontSize = 20.sp,
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold
                )

                Text(text = "Capacity: ${room.capacity} people", fontSize = 16.sp, color = Color.Gray)

                // Displaying features/amenities
                Text(text = "Features:", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                room.features.forEach { feature ->
                    Text(text = "• $feature", fontSize = 16.sp, color = Color.Gray)
                }

                // Availability information
                Text(
                    text = "Available: ${if (room.availability.isAvailable) "Yes" else "No"}",
                    fontSize = 16.sp,
                    color = if (room.availability.isAvailable) Color.Green else Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back button
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.End),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Back")
        }
    }
}
