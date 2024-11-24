package com.example.gotravel.ui.module.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Rating
import com.example.gotravel.helper.CommonUtils
import com.example.gotravel.ui.module.accomodation.HotelInfoSection
import com.example.gotravel.ui.module.accomodation.ImageSection
import java.math.BigDecimal
import java.math.RoundingMode

@Preview(showSystemUi = true)
@Composable
fun ListReviewScreen(
    accommodation: Accommodation= Accommodation(),
    navController: NavController = NavController(LocalContext.current)
) {
    val averageRating = if (accommodation.ratings.isNotEmpty()) {
        BigDecimal(accommodation.ratings.map { it.rate }.average())
            .setScale(1, RoundingMode.HALF_UP)
            .toDouble()
    } else {
        5.0
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Column(modifier = Modifier.padding(16.dp))
                    {
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
                    HorizontalDivider(thickness = 7.dp, color = colorResource(R.color.lightGray))

                    if (accommodation.ratings.isEmpty()){
                        Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painter = painterResource(id = R.drawable.ic_no_review),
                                contentDescription = null,
                                modifier = Modifier.padding(bottom = 20.dp).size(150.dp)
                            )
                            Text(text = "Chưa có đánh giá",
                                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                                fontSize = 20.sp)
                        }
                    }else{
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Các lượt đánh giá",
                                fontFamily = FontFamily(Font(R.font.proxima_nova_bold)),
                                fontSize = 22.sp)
                            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(top = 16.dp))
                            accommodation.ratings.forEach{rating ->
                                ReviewItem(rating)
                            }


                        }
                    }
                }

            }
        }
    }
}
@Composable
fun ReviewItem(
    rating: Rating
){
    Column {
        Row(modifier = Modifier
            .padding(top = 16.dp, bottom = 5.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically,) {
                Image(painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 8.dp),
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
            modifier = Modifier.padding(start = 30.dp, top = 5.dp)
        )
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
    }
}