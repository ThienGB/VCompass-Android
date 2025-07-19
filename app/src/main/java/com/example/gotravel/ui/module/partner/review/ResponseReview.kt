package com.example.gotravel.ui.module.partner.review

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Rating
import com.example.gotravel.helper.CommonUtils
import com.example.gotravel.ui.module.general.accomodation.HotelInfoSection
import com.example.gotravel.ui.module.general.accomodation.ImageSection
import com.example.gotravel.ui.module.partner.main.MainPartnerViewModel
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun ResponseReviewScreen(
    accommodation: Accommodation= Accommodation(),
    viewModel: MainPartnerViewModel,
    rating: Rating,
    navController: NavController = NavController(LocalContext.current)
) {
    val context = LocalContext.current
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
            ImageSection(accommodation.image) { navController.popBackStack() }
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
                Row(modifier = Modifier
                    .padding(top = 16.dp, bottom = 5.dp)
                    .fillMaxWidth()
                    .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,) {
                        Image(painter = painterResource(id = R.drawable.ic_user),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(end = 8.dp)
                                .background(Color.Transparent, RoundedCornerShape(99.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = rating.userName,
                            fontWeight = Bold,
                            fontSize = 18.sp,
                        )
                    }
                    Text(
                        text = CommonUtils.formatDate(rating.createdAt)   ,
                        color = Color.Gray,
                    )
                }
                Row (modifier = Modifier.padding(start = 30.dp)) {
                    for (i in 1..rating.rate){
                        Icon(imageVector = Icons.Default.Star,
                            contentDescription = null, tint = Color.Yellow)
                    }
                }
                Text(
                    text = rating.content,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 40.dp, top = 5.dp, bottom = 15.dp)
                )
                HorizontalDivider(
                    thickness = 7.dp,
                    color = colorResource(R.color.lightGray)
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Viết phản hồi",
                        fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                        fontSize = 22.sp)
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { newText ->
                            reviewText = newText
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        label = { Text("Nhập nội dung phản hồi") },
                        placeholder = { Text("Viết ý kiến của bạn...") },
                        singleLine = false,
                        maxLines = 4
                    )
                    Text(
                        text = "Bất kể dài hay ngắn, hãy phản hồi lại nguời dùng",
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
                            viewModel.addResponseRating(accommodation.accommodationId, rating.ratingId, reviewText)
                            navController.navigate("list_rating")
                            Toast.makeText(context, "Phản hồi thành công", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Text(text = "Gửi phản hồi", color = Color.White,
                        fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                        fontSize = 20.sp)
                }
            }
        }
    }
}
