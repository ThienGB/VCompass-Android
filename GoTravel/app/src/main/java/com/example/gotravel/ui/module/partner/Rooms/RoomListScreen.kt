package com.example.myapp.ui.rooms

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gotravel.R
import com.example.gotravel.ui.module.partner.Rooms.Availability
import com.example.gotravel.ui.module.partner.Rooms.Image
import com.example.gotravel.ui.module.partner.Rooms.Room
import com.example.gotravel.ui.module.partner.Rooms.RoomCard

@Composable
fun RoomListScreen(navController: NavController) {
    // Define a list of rooms with appropriate data
    val rooms = listOf(
        Room(
            roomNumber = "101",
            roomType = "Deluxe King Room",
            price = 3493.0,
            capacity = 2,
            features = listOf("Non-Refundable", "Price for 1 Adult"),
            image = Image(url = R.drawable.accommodation_sample.toString(), alt = "Deluxe King Room"),
            availability = Availability(isAvailable = true),
            createdAt = "2024-10-01",
            updatedAt = "2024-10-10"
        ),
        Room(
            roomNumber = "102",
            roomType = "Standard Queen Room",
            price = 2999.0,
            capacity = 3,
            features = listOf("Non-Refundable", "Price for 1 Adult"),
            image = Image(url = R.drawable.accommodation_sample.toString(), alt = "Standard Queen Room"),
            availability = Availability(isAvailable = true),
            createdAt = "2024-10-01",
            updatedAt = "2024-10-10"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Button to navigate to the AddUpdateRoomScreen
        Button(
            onClick = { navController.navigate("AddUpdateRoomScreen/add/0000") },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add Room")
        }

        // LazyColumn to display the list of rooms
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(rooms.size) { index ->
                RoomCard(room = rooms[index], navController = navController)
            }
        }
    }
}
