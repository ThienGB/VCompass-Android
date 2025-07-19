package com.example.vcompass.ui.module.user.booking

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vcompass.R
import com.example.vcompass.data.model.Booking
import com.example.vcompass.helper.CommonUtils.formatDateHaveDay
import com.example.vcompass.ui.components.NavTitle

@Composable
fun BookingInformation(
    booking: Booking = Booking(),
    viewModel: BookingListViewModel,
    navController: NavController = NavController(LocalContext.current),
) {
    val statusString = when (booking.status) {
        "pending" -> "Đang xử lý"
        "success" -> "Đặt Thành công"
        "failed" -> "Thất bại"
        else -> {"Hủy"}
    }
    val textColor = when (booking.status) {
        "pending" -> Color(0xFFC7A53E)
        "success" -> Color(0xFF4CAF50)
        "failed" -> Color(0xFFC7372D)
        else -> {Color(0xFF050505)
        }
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        Text(
            text = statusString,
            color = textColor,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FD))
                .padding(16.dp)
                .clickable { viewModel.setAccommodation(booking.accommodationId)
                    navController.navigate("accom_detail")
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_accommodation),
                contentDescription = "Hotel",
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = booking.accommodationName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = (((booking.endDate - booking.startDate).toInt()) / 86400000).toString() + " đêm",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        BookingDetailItem(icon = Icons.Default.Info, label = "Thông tin khách", value = booking.fullName)
        BookingDetailItem(icon = Icons.Default.Call, label = "Số điện thoại", value = booking.phoneNumber)
        BookingDetailItem(icon = Icons.Default.Email, label = "Email", value = booking.email)
        HorizontalDivider(thickness = 1.dp)
        BookingDetailItem(
            icon = Icons.Default.DateRange,
            label = "Nhận phòng",
            value = formatDateHaveDay(booking.startDate) + " (12:00 - 14:00)"
        )
        BookingDetailItem(
            icon = Icons.Default.DateRange,
            label = "Trả phòng",
            value = formatDateHaveDay(booking.endDate) + " (trước 11:00)"
        )
        HorizontalDivider(thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))
        PolicyItem(text = "Miễn phí hủy phòng", icon = Icons.Default.CheckCircle, iconColor = Color(0xFF4CAF50))
        PolicyItem(
            text = "Áp dụng chính sách đổi lịch",
            icon = Icons.Default.CheckCircle,
            iconColor = Color(0xFF4CAF50)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(8.dp))
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Mã đặt chỗ",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = booking.bookingId,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_copy),
                    contentDescription = "Copy",
                    tint = colorResource(id = R.color.primary),
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            val clipboard =
                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Booking ID", booking.bookingId)
                            clipboard.setPrimaryClip(clip)
                            Toast
                                .makeText(context, "Đã sao chép mã đặt chỗ", Toast.LENGTH_SHORT)
                                .show()
                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, bottom = 16.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(99.dp))
                .background(colorResource(id = R.color.primary))
                .clickable {
                    viewModel.getAccomById()
                    navController.navigate("rating")
                }
        ) {
            Image(painter = painterResource(id = R.drawable.ic_rating),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(25.dp),
                colorFilter = ColorFilter.tint(Color.White))
            Text(text = "Đánh giá chỗ ở", color = Color.White,
                fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                fontSize = 20.sp, modifier = Modifier.padding(end = 70.dp))
        }
    }

}

@Composable
fun BookingDetailItem(icon: ImageVector, label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.Gray,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontSize = 13.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun PolicyItem(text: String, icon: ImageVector, iconColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun BookingInforScreen(
    booking: Booking,
    viewModel: BookingListViewModel,
    navController: NavController,
){
    Column {
        NavTitle("Thông tin đặt phòng"){navController.navigate("booking_list")}
        BookingInformation(booking,viewModel, navController)
    }
}