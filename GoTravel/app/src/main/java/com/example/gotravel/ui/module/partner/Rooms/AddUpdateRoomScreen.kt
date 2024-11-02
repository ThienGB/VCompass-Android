package com.example.gotravel.ui.module.partner.Rooms

import android.Manifest
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotravel.ui.module.partner.Rooms.Room
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AddUpdateRoomScreen(
    navController: NavController,
    mode: String,
    room: Room?
) {
    // Variables to hold room details
    var roomNumber by remember { mutableStateOf("") }
    var roomType by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
    var isAvailable by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Set values if mode is "update"
    if (mode == "update" && room != null) {
        roomNumber = room.roomNumber
        roomType = room.roomType
        price = room.price.toString()
        capacity = room.capacity.toString()
        isAvailable = room.availability.isAvailable
    }

    // List of available features
    val availableFeatures = listOf("WiFi", "Air Conditioning", "TV", "Mini Bar", "Pool Access")
    val selectedFeatures = remember { mutableStateListOf<String>() }

    // Permission state for image selection
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )

    // Activity result launcher for image picking
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Text(
            text = if (mode == "add") "Add Room" else "Update Room",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Image Picker Button
        Button(
            onClick = {
                permissionState.launchMultiplePermissionRequest()
                if (permissionState.allPermissionsGranted) {
                    launcher.launch("image/*")
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Select Image", color = Color.White)
        }

        // Display Selected Image
        selectedImageUri?.let { uri ->
            val inputStream: InputStream? = LocalContext.current.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Selected Room Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Room Number Input
        OutlinedTextField(
            value = roomNumber,
            onValueChange = { roomNumber = it },
            label = { Text("Room Number") },
            modifier = Modifier.fillMaxWidth()
        )

        // Room Type Dropdown
        var expanded by remember { mutableStateOf(false) }
        val roomTypeOptions = listOf("Single", "Double", "Suite")
        // Room Type Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = roomType,
                onValueChange = {},
                label = { Text("Room Type") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }, // Ensures clicking triggers expansion
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                roomTypeOptions.forEach { type ->
                    DropdownMenuItem(
                        text = { Text(text = type) },
                        onClick = {
                            roomType = type
                            expanded = false
                        }
                    )
                }
            }
        }

        // Price Input
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        // Capacity Input
        OutlinedTextField(
            value = capacity,
            onValueChange = { capacity = it },
            label = { Text("Capacity") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        // Availability Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Checkbox(
                checked = isAvailable,
                onCheckedChange = { isAvailable = it },
                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
            )
            Text(text = "Available", fontSize = 16.sp)
        }

        // Features Checkboxes
        Text(text = "Select Features:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        availableFeatures.forEach { feature ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selectedFeatures.contains(feature),
                    onCheckedChange = { checked ->
                        if (checked) {
                            selectedFeatures.add(feature)
                        } else {
                            selectedFeatures.remove(feature)
                        }
                    },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Text(text = feature, fontSize = 16.sp)
            }
        }

        // Save Button
        Button(
            onClick = {
                // Handle save or update logic here, including room data
                // Example: create or update Room object
            },
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = if (mode == "add") "Add Room" else "Update Room", color = Color.White)
        }
    }
}
