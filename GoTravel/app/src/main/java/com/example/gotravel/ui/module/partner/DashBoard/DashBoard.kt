package com.example.gotravel.ui.module.partner.DashBoard


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.R
import com.example.gotravel.data.model.Accommodation
import com.example.gotravel.data.model.Booking
import com.example.gotravel.data.model.Room
import com.example.gotravel.helper.CommonUtils.formatCurrency
import com.example.gotravel.ui.components.Loading
import com.example.gotravel.ui.module.accomodation.TopReviewSection
import com.example.gotravel.ui.module.main.partner.MainPartnerViewModel

@Composable
fun DashboardScreen(
    accommodation: Accommodation = Accommodation(),
    bookings: List<Booking> = listOf(),
    isLoading: Boolean = true,
    navController: NavController,
    viewModel: MainPartnerViewModel
) {
    viewModel.setRoom(Room())
    if (isLoading) {
        Loading()
    } else {
        val topRatings = accommodation.ratings.take(5)
        Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Image(painter = painterResource(id = R.drawable.bg_partner),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(10.dp)
            ) {
                if (accommodation.accommodationId == ""){
                    Spacer(modifier = Modifier.height(30.dp))
                    AddHotelCard(navController)
                    Spacer(modifier = Modifier.height(145.dp))
                    Text(
                        text = "Bạn chưa có khách sạn nào, hãy thêm để sử dụng đầy đủ dịch vụ",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                    )
                } else if (accommodation.status == "pending"){
                    Spacer(modifier = Modifier.height(30.dp))
                    HotelInfoCard(accommodation, navController)
                    Spacer(modifier = Modifier.height(145.dp))
                    Text(
                        text = "Khách sạn của bạn đang được xét duyệt, vui lòng chờ trong giây lát",
                        fontSize = 18.sp,
                        color = Color(0xFFD6A100),
                        textAlign = TextAlign.Center,
                    )
                }
                else{
                    Spacer(modifier = Modifier.height(30.dp))
                    HotelInfoCard(accommodation, navController)
                    Spacer(modifier = Modifier.height(115.dp))
                    Text(
                        text = "Tổng quan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                    BookingStats(bookings, navController)
                    Spacer(modifier = Modifier.height(16.dp))
                    RoomsListed(accommodation, viewModel, navController)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Đánh giá",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TopReviewSection(topRatings)
                }
            }
        }
    }

}
@Composable
fun AddHotelCard(
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = 0.9f)
            .clickable { navController.navigate("add_accom") }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_accom),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Transparent, RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(30.dp))
                Text(text = "Thêm khách sạn", fontWeight = FontWeight.Bold, fontSize = 25.sp)

            }
        }
    }
}
@Composable
fun HotelInfoCard(
    accommodation: Accommodation = Accommodation(),
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = 0.9f)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(accommodation.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Transparent, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = accommodation.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = accommodation.city, color = Color.Gray, fontSize = 14.sp)

                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .background(Color.Transparent, RoundedCornerShape(12.dp))
                        .clickable { navController.navigate("add_accom") },
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
fun BookingStats(
   bookings: List<Booking> = listOf(),
   navController: NavController
) {
    val revenue = bookings.sumOf { booking -> booking.price }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "DOANH THU", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = formatCurrency(revenue.toString())+ " đ", color = colorResource(id = R.color.green),
                    fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Tổng doanh thu",color = colorResource(id = R.color.primary),
                    fontStyle = FontStyle.Italic, fontSize = 16.sp)
            }
        }
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .clickable { navController.navigate("booking_partner") }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "ĐẶT PHÒNG", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = bookings.size.toString(),color = colorResource(id = R.color.green),
                    fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Xem chi tiết",color = colorResource(id = R.color.primary),
                    fontStyle = FontStyle.Italic, fontSize = 16.sp, textDecoration = TextDecoration.Underline)
            }
        }
    }
}

@Composable
fun RoomsListed(
    accommodation: Accommodation = Accommodation(),
    viewModel: MainPartnerViewModel,
    navController: NavController
) {
    Column{
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)) {
            Text(
                text = "Danh sách phòng",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.clickable {
                viewModel.setRoom(Room())
                navController.navigate("add_room")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add",
                    tint = colorResource(id = R.color.primary),
                    modifier = Modifier.padding(bottom = 5.dp))
                Text(
                    text = "Thêm phòng",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.primary),
                )
            }
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(accommodation.rooms){ room ->
                RoomCard(room, viewModel, navController)
            }
        }
    }
}

@Composable
fun RoomCard(
    room: Room = Room(),
    viewModel: MainPartnerViewModel,
    navController: NavController
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
    ) {
        Column(modifier = Modifier
            .padding(10.dp)
            .width(150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = room.name,
                maxLines = 2,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp)
            Text(text = "Số giường: "+ room.bed.toString(),
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
                )
            Text(text = "Số nguời: " + room.people.toString(),
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
            )
            Text(text = "Loại phòng: " + room.roomType,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Gray,
            )
            Text(text = formatCurrency(room.price.toString()) + " đ",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Right,
                color = colorResource(id = R.color.green),

            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(colorResource(id = R.color.primary))
                    .clickable {
                        viewModel.setRoom(room)
                        navController.navigate("add_room")
                    }

            ) {
                Text(text = "Chỉnh sửa", color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 15.sp)
            }
        }
    }
}

