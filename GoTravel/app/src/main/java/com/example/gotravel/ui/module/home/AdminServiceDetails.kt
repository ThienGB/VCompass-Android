package com.example.gotravel.ui.module.home

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotravel.R

class AdminServiceDetails : ViewModel()
{
    private val _text = MutableLiveData<String>().apply {
        value = "Admin xem chi tiết dịch vụ"
    }
    val text: LiveData<String> = _text
}
@Preview
@Composable
fun AdminServiceDetailsPreview()
{
    AdminServiceDetailsScreen()
}
@Composable
fun AdminServiceDetailsScreen()
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),  // Thay đổi màu nền thành trắng
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF1A94FF))  // Màu nền xanh
                .padding(20.dp),
            contentAlignment = Alignment.Center // Căn giữa nội dung trong Box
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween, // Căn dưới cho nội dung trong Column
                horizontalAlignment = Alignment.Start // Căn trái cho text
            ) {
                Spacer(modifier = Modifier.weight(1f))
                // Tiêu đề "Gửi thông báo"
                Text(
                    text = "Chi tiết dịch vụ", // Tiêu đề
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,  // Màu chữ trắng
                    modifier = Modifier.padding() // Khoảng cách trên
                )
            }
        }

        // Danh sách các chức năng
        Spacer(modifier = Modifier.height(20.dp))

        ServiceInfo(
            text = "Tên dịch vụ:",
            startIcon = R.drawable.ic_service_management, // Thay thế bằng icon phù hợp
            content = "Hotel chữa lành",
            onClick = { /* Hành động */ }
        )
        ServiceInfo(
            text = "Parner phụ trách:",
            startIcon = R.drawable.ic_user_info, // Thay thế bằng icon phù hợp
            content = "Thoại",
            onClick = { /* Hành động */ }
        )

        ServiceInfo(
            text = "Địa chỉ:",
            startIcon = R.drawable.ic_location, // Thay thế bằng icon phù hợp
            content = "TP Hồ Chí minh",
            onClick = { /* Hành động */ }
        )
        ServiceInfo(
            text = "Giờ mở cửa:",
            startIcon = R.drawable.ic_calendar, // Thay thế bằng icon phù hợp
            content = "7:00",
            onClick = { /* Hành động */ }
        )
        ServiceInfo(
            text = "Giờ đóng cửa",
            startIcon = R.drawable.ic_calendar, // Thay thế bằng icon phù hợp
            content = "22:00",
            onClick = { /* Hành động */ }
        )
        ServiceInfo(
            text = "Trạng thái",
            startIcon = R.drawable.ic_password, // Thay thế bằng icon phù hợp
            content = "Đang hoạt động",
            onClick = { /* Hành động */ }
        )
        ServiceInfo(
            text = "Ngày đăng ký",
            startIcon = R.drawable.ic_calendar, // Thay thế bằng icon phù hợp
            content = "20/10/2024",
            onClick = { /* Hành động */ }
        )
        ServiceInfo(
            text = "Ảnh giới thiêu",
            startIcon = R.drawable.ic_calendar, // Thay thế bằng icon phù hợp
            content = "",
            onClick = { /* Hành động */ }
        )


        // Nút "Duyệt" và "Từ chối"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Duyệt */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A94FF)),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Khóa dịch vụ")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { /* Từ chối */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Xóa dịch vụ")
            }
        }

    }
}
@Composable
fun ServiceInfo(
    text: String,
    startIcon: Int,
    content: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 15.dp),  // Khoảng cách giữa các mục
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon ở đầu
        Image(
            painter = painterResource(id = startIcon),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .padding(end = 8.dp)
        )

        // Text chức năng
        Text(
            text = text,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        // Nội dung dữ liệu
        Box(
            modifier = Modifier.width(130.dp) // Đặt kích thước cho Box
        ) {
            Text(
                text = content,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterEnd), // Căn phải văn bản
                maxLines = 1, // Giới hạn số dòng
                overflow = TextOverflow.Ellipsis // Hiển thị dấu ... nếu văn bản quá dài
            )
        }

        // Icon ở cuối
        Image(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .padding(start = 8.dp)
        )
    }
}