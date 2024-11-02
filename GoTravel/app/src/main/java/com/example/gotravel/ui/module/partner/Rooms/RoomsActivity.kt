package com.example.gotravel.ui.module.partner.Rooms

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gotravel.R
import com.example.myapp.ui.rooms.RoomListScreen

class RoomsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                SetupNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "RoomListScreen") {
        composable("RoomListScreen") { RoomListScreen(navController = navController) }
        composable("AddUpdateRoomScreen/{mode}/{roomNumber}") { backStackEntry ->
            val mode = backStackEntry.arguments?.getString("mode") ?: "add"
            val roomNumber = backStackEntry.arguments?.getString("roomNumber")
            Log.d("Room number:", "$roomNumber")
            val room = getRoomByNumber(roomNumber)
            Log.d("Room:", "$room")

                AddUpdateRoomScreen(
                    navController = navController, mode = mode,
                    room = room
                )



        }
        composable("RoomInfoScreen/{roomNumber}") { backStackEntry ->
            val roomNumber = backStackEntry.arguments?.getString("roomNumber")
            val room = getRoomByNumber(roomNumber)
            room?.let {
                RoomInfoScreen(navController = navController, room = it)
            }
        }
    }
}


// Example function to get a room by number
fun getRoomByNumber(roomNumber: String?): Room? {
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
    return rooms.find { it.roomNumber == roomNumber }
}


