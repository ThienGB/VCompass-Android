package com.example.gotravel.ui.module.review

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Rating
import com.example.gotravel.data.model.User
import com.example.gotravel.ui.module.accomodation.HotelInfoSection
import com.example.gotravel.ui.module.accomodation.ImageSection
import com.example.gotravel.ui.module.booking.BookingListViewModel
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@Composable
fun ReviewScreen(
    accommodation: Accommodation= Accommodation(),
    viewModel: BookingListViewModel,
    user: User,
    navController: NavController = NavController(LocalContext.current)
) {
    val context = LocalContext.current
    var currentRating by remember { mutableStateOf(1) }
    var reviewText by remember { mutableStateOf("") }
    val averageRating = if (accommodation.ratings.isNotEmpty()) {
        BigDecimal(accommodation.ratings.map { it.rate }.average())
            .setScale(1, RoundingMode.HALF_UP)
            .toDouble()
    } else {
        5.0
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Box{
            ImageSection(accommodation.image) { navController.navigate("booking_infor") }
            Column (modifier = Modifier
                .padding(top = 180.dp)
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
            ) {
                HotelInfoSection(
                    title = accommodation.name,
                    numStart = averageRating.toInt(),
                    location = accommodation.address
                )
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Xếp hạng và đánh giá",
                            fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                            fontSize = 22.sp)
                    }
                    Row(modifier = Modifier.padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = averageRating.toString(), fontSize = 50.sp,
                            fontFamily = FontFamily(Font(R.font.proxima_nova_semi_bold)),
                            color = colorResource(id = R.color.primary))
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(text = "Ấn tượng", fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                                color = colorResource(id = R.color.primary))
                            Text(text = accommodation.ratings.size.toString() + " lượt đánh giá", fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)))
                        }
                    }

                }
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Viết đánh giá",
                        fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                        fontSize = 22.sp)
                    Row(modifier = Modifier.padding(vertical = 16.dp)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        for (i in 1..5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (i <= currentRating) Color.Yellow else Color.Gray,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable {
                                        currentRating = i
                                    }
                            )
                        }
                    }
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { newText ->
                            reviewText = newText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        label = { Text("Nhập nội dung đánh giá") },
                        placeholder = { Text("Viết ý kiến của bạn...") },
                        singleLine = false,
                        maxLines = 4
                    )
                    Text(
                        text = "Bất kể dài hay ngắn, đánh giá của bạn sẽ giúp chúng tôi cải thiện chất lượng dịch vụ",
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(colorResource(id = R.color.primary))
                        .clickable {
                            val rating = Rating().apply {
                                rate = currentRating
                                content = reviewText
                                userId = user.userId
                                userName = user.name
                                createdAt = System.currentTimeMillis()
                                ratingId = UUID.randomUUID().toString()

                            }
                            viewModel.insertReview(rating)
                            navController.navigate("booking_infor")
                            Toast.makeText(context, "Đánh giá thành công", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Text(text = "Gửi đánh giá", color = Color.White,
                        fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                        fontSize = 20.sp)
                }
            }
        }
    }
}
