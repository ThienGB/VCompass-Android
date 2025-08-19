//package com.example.vcompass.ui.module.partner.booking
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.Font
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import com.example.vcompass.helper.CommonUtils.formatCurrency
//import com.example.vcompass.ui.components.CustomSearchBar
//import com.example.vcompass.ui.components.NavTitle
//import com.example.vcompass.ui.module.partner.main.MainPartnerViewModel
//import com.example.vcompass.R
//
//@Composable
//fun BookingListPartner(
//    bookings: List<Booking> = emptyList(),
//    viewModel: MainPartnerViewModel,
//    navController: NavController
//) {
//    var filterBooking by remember { mutableStateOf(bookings) }
//    fun onTextChange(query: String){
//        filterBooking = bookings.filter { booking ->
//            booking.accommodationName.contains(query, ignoreCase = true) ||
//            booking.fullName.contains(query, ignoreCase = true)
//        }
//    }
//    Column {
//        Column {
//            NavTitle("Danh sách đặt phòng") { navController.popBackStack() }
//            CustomSearchBar(onTextChange = { onTextChange(it) }, "Tìm kiếm theo mã hoặc tên")
//        }
//        Spacer(modifier = Modifier.height(15.dp))
//        if (bookings.isEmpty()) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_no_booking),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(bottom = 20.dp)
//                        .size(150.dp)
//                )
//                Text(
//                    text = "Chưa có khách hàng nào đặt chỗ",
//                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
//                    fontSize = 20.sp
//                )
//            }
//        } else {
//            LazyColumn {
//                items(filterBooking) { booking ->
//                    BookingPartnerItem(booking, navController, viewModel)
//                }
//            }
//        }
//    }
//}
//@Composable
//fun BookingPartnerItem(
//    booking: Booking,
//    navController: NavController,
//    viewModel: MainPartnerViewModel
//) {
//    val backgroundColor = when (booking.status) {
//        "pending" -> Color(0xFFFAEDC4)
//        "success" -> Color(0xFFCDFCCE)
//        "failed" -> Color(0xFFFAD4D1)
//        else -> {Color(0xFFCACACA)
//        }
//    }
//    val statusString = when (booking.status) {
//        "pending" -> "Đang chờ duyệt"
//        "success" -> "Đã duyệt"
//        "failed" -> "Đã từ chối"
//        else -> {"Hủy"}
//    }
//    val textColor = when (booking.status) {
//        "pending" -> Color(0xFFCF9B00)
//        "success" -> Color(0xFF008005)
//        "failed" -> Color(0xFFB90C00)
//        else -> {Color(0xFF050505)
//        }
//    }
//    Card(
//        elevation = CardDefaults.cardElevation(4.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .clickable {
//                viewModel.setBooking(booking)
//                navController.navigate("booking_infor_partner")
//            }
//    ) {
//        Column(modifier = Modifier
//            .background(Color.White)
//            .padding(16.dp)
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(bottom = 8.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .background(color = backgroundColor, shape = RoundedCornerShape(30.dp))
//                        .padding(horizontal = 20.dp, vertical = 6.dp)
//                ) {
//                    Text(
//                        text = statusString,
//                        color = textColor,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight(450)
//                    )
//                }
//            }
//            Row (verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.padding(vertical = 10.dp)) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_user),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .padding(end = 8.dp)
//                        .size(24.dp)
//                )
//                Text(
//                    text = booking.fullName,
//                    fontWeight = FontWeight(600),
//                    fontSize = 18.sp
//                )
//            }
//            HorizontalDivider(thickness = 1.dp)
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 10.dp)
//            ) {
//                Text(
//                    text = "Mã: ${booking.bookingId}",
//                )
//
//                Text(
//                    text = formatCurrency(booking.price.toString()) + " đ",
//                    color = colorResource(id = R.color.primary),
//                    fontWeight = FontWeight(600),
//                    fontSize = 16.sp
//                )
//            }
//        }
//    }
//}
