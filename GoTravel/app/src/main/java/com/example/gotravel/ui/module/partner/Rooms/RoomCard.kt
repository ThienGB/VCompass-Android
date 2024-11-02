package com.example.gotravel.ui.module.partner.Rooms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun RoomCard(room: Room, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { navController.navigate("RoomInfoScreen/${room.roomNumber}") },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Display room image
            Image(
                painter = rememberImagePainter(room.image.url),
                contentDescription = room.image.alt,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Room details
            Text(text = "Room Number: ${room.roomNumber}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Type: ${room.roomType}", fontSize = 16.sp, color = Color.Gray)
            Text(text = "Capacity: ${room.capacity} People", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Price: ₹${room.price}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
            Text(
                text = if (room.availability.isAvailable) "Available" else "Unavailable",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (room.availability.isAvailable) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Room features
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (feature in room.features) {
                    Text(text = feature, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("AddUpdateRoomScreen/update/${room.roomNumber}")
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Edit")
            }
        }
    }
}
