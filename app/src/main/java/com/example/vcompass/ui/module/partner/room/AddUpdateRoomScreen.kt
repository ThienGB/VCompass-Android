package com.example.vcompass.ui.module.partner.room

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vcompass.R
import com.example.vcompass.data.model.Accommodation
import com.example.vcompass.data.model.Room
import com.example.vcompass.helper.CommonUtils.upLoadImage
import com.example.vcompass.ui.components.Loading
import com.example.vcompass.ui.components.NavTitle
import com.example.vcompass.ui.module.partner.main.MainPartnerViewModel

@Composable
fun AddUpdateRoomScreen(
    navController: NavController = NavController(LocalContext.current),
    accommodation: Accommodation = Accommodation(),
    room: Room = Room(),
    viewModel: MainPartnerViewModel
) {
    var roomId by remember { mutableStateOf(room.roomId) }
    var name by remember { mutableStateOf(room.name) }
    var roomType by remember { mutableStateOf(room.roomType) }
    var price by remember { mutableIntStateOf(room.price) }
    var people by remember { mutableIntStateOf(room.people) }
    var bed by remember { mutableIntStateOf(room.bed) }
    var area by remember { mutableIntStateOf(room.area) }
    var image by remember { mutableStateOf(room.image) }
    var status by remember { mutableStateOf(room.status) }
    val statusOptions = listOf("Active", "Non Active")
    val roomTypeOptions = listOf("S", "M", "L")
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }
    var isLoading by remember { mutableStateOf(false) }
    Column {
        NavTitle(accommodation.name) { navController.popBackStack() }
        Box(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp))
        {
            Card (
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(8.dp),
            ){
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedImageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else if (image !=  "") {
                    Image(
                        painter = rememberAsyncImagePainter(image),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.bg_partner),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .graphicsLayer(alpha = 0.8f)
                    .padding(top = 150.dp, start = 70.dp, end = 70.dp)
                    .fillMaxWidth()
                    .width(200.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(99.dp))
                    .background(colorResource(id = R.color.primary))
                    .clickable { launcher.launch("image/*") }

            ) {
                Image(painter = painterResource(id = R.drawable.ic_upload),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .size(25.dp),
                    colorFilter = ColorFilter.tint(Color.White))
                Text(text = "Chọn ảnh", color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 17.sp)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tên phòng") },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownField(
                label = "Loại phòng",
                options = roomTypeOptions,
                selectedOption = roomType,
                onOptionSelected = { roomType = it }
            )
            OutlinedTextField(
                value = price.toString(),
                onValueChange = { price = it.toIntOrNull() ?: 0 },
                label = { Text("Giá một đêm") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            // People Input
            OutlinedTextField(
                value = people.toString(),
                onValueChange = { people = it.toIntOrNull() ?: 0 },
                label = { Text("Số người ở") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            // Bed Input
            OutlinedTextField(
                value = bed.toString(),
                onValueChange = { bed = it.toIntOrNull() ?: 0 },
                label = { Text("Số giường") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            // Area Input
            OutlinedTextField(
                value = area.toString(),
                onValueChange = { area = it.toIntOrNull() ?: 0 },
                label = { Text("Diện tích") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
            DropdownField(
                label = "Trạng thái",
                options = statusOptions,
                selectedOption = status,
                onOptionSelected = { status = it }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(colorResource(id = R.color.primary))
                    .clickable {
                        if (selectedImageUri != null) {
                            isLoading = true
                            upLoadImage(selectedImageUri!!, "room"){ uploadedImageUrl ->
                                image = uploadedImageUrl
                                viewModel.insertRoom(roomId, name, roomType, price, people, bed, image, area, status)
                                isLoading = false
                                navController.navigate("home")
                                if (roomId == "")
                                    Toast.makeText(context, "Thêm phòng thành công", Toast.LENGTH_SHORT).show()
                                else
                                    Toast.makeText(context, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show()
                            }
                        }else {
                            viewModel.insertRoom(roomId, name, roomType, price, people, bed, image, area, status)
                            navController.navigate("home")
                            if (roomId == "")
                                Toast.makeText(context, "Thêm phòng thành công", Toast.LENGTH_SHORT).show()
                            else
                                Toast.makeText(context, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show()
                        }

                    }
            ) {
                Text(text = "Xác nhận", color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp)
            }
        }
    }
    if (isLoading)
        Loading()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

