package com.example.gotravel.ui.module.partner.accommodation

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.helper.CommonUtils.upLoadImage
import com.example.gotravel.ui.components.Loading
import com.example.gotravel.ui.components.NavTitle
import com.example.gotravel.ui.module.main.partner.MainPartnerViewModel

@Composable
fun AddAccomScreen(
    navController: NavController = NavController(LocalContext.current),
    accommodation: Accommodation = Accommodation(),
    viewModel: MainPartnerViewModel
) {
    var name by remember { mutableStateOf(accommodation.name) }
    var address by remember { mutableStateOf(accommodation.address) }
    var city by remember { mutableStateOf(accommodation.city) }
    var description by remember { mutableStateOf(accommodation.description) }
    var amentities by remember { mutableStateOf(accommodation.amentities) }
    var image by remember {mutableStateOf(accommodation.image)}
    val amentitiesOptions = listOf("Nhà hàng", "Lễ Tân 24h","Hồ Bơi", "Wifi","Massage", "Thú Cưng")
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }
    val context = LocalContext.current
    val title = if (accommodation.accommodationId == "") "Thêm chỗ ở" else "Cập nhật chỗ ở"
    var isLoading by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFEDFAFC))) {
        NavTitle(title) { navController.popBackStack() }
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
                label = { Text("Tên chỗ ở") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Tên thành phố") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = address,
                onValueChange = { newText ->
                    address = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                label = { Text("Địa chỉ") },
                placeholder = { Text("Nhập địa chỉ... ") },
                singleLine = false,
            )
            OutlinedTextField(
                value = description,
                onValueChange = { newText ->
                    description = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                label = { Text("Mô tả") },
                placeholder = { Text("Nhập mô tả... ") },
                singleLine = false,
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                amentitiesOptions.forEach { option ->
                    val isChecked = amentities.contains(option)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {},
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {checked ->
                                amentities = if (checked) {
                                    if (amentities.isEmpty()) option else "$amentities, $option"
                                } else {
                                    amentities.split(", ")
                                        .filter { it != option }
                                        .joinToString(", ")
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                uncheckedColor = colorResource(id = R.color.green),
                                checkedColor = colorResource(id = R.color.green))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = option, fontSize = 16.sp)
                    }
                }
            }


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
                        fun handleAccommodationAction(imageUrl: String) {
                            if (accommodation.accommodationId.isEmpty()) {
                                // Thêm mới
                                viewModel.insertAccom(
                                    name,
                                    imageUrl,
                                    description,
                                    address,
                                    city,
                                    amentities
                                )
                                Toast.makeText(context, "Thêm thành công, vui lòng chờ xét duyệt", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.updateAccom(
                                    accommodation.accommodationId,
                                    name,
                                    imageUrl,
                                    description,
                                    address,
                                    city,
                                    amentities
                                )
                                Toast.makeText(context, "Cập nhật thành công, vui lòng chờ xét duyệt", Toast.LENGTH_SHORT).show()
                            }
                            isLoading = false
                            navController.popBackStack()
                        }
                        selectedImageUri?.let { uri ->
                            isLoading = true
                            upLoadImage(uri, "accommodation") { uploadedImageUrl ->
                                handleAccommodationAction(uploadedImageUrl)
                            }
                        } ?: run {
                            handleAccommodationAction(image)
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


