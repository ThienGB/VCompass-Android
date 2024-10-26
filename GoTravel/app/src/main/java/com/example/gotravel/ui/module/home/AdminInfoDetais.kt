package com.example.gotravel.ui.module.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.ui.res.painterResource
import com.example.gotravel.R

class AdminInfoDetais : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "User Info"
    }
    val text: LiveData<String> = _text
}

@Composable
fun AdminInfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),  // Thay đổi màu nền thành trắng
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box màu xanh chứa avatar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF1A94FF))  // Màu nền xanh
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center // Căn giữa nội dung trong Box
        ) {
            // Avatar tròn lớn
            Box(
                modifier = Modifier
                    .size(80.dp)  // Kích thước avatar
                    .background(Color.Gray, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Avatar",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Danh sách các chức năng
        Spacer(modifier = Modifier.height(20.dp))

        UserInfoListItem(
            text = "Tên:",
            startIcon = R.drawable.ic_user_info, // Thay thế bằng icon phù hợp
            content = "Nguyễn Văn A",
            onClick = { /* Hành động */ }
        )

        UserInfoListItem(
            text = "Số điện thoại:",
            startIcon = R.drawable.ic_phone, // Thay thế bằng icon phù hợp
            content = "0123456789",
            onClick = { /* Hành động */ }
        )

        UserInfoListItem(
            text = "Địa chỉ:",
            startIcon = R.drawable.ic_location, // Thay thế bằng icon phù hợp
            content = "Hà Nội",
            onClick = { /* Hành động */ }
        )

        // Nút Lưu
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /* Hành động lưu */ },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Lưu", fontSize = 18.sp)
        }
    }
}

@Composable
fun UserInfoListItem(
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
        Text(
            text = content,
            fontSize = 18.sp,
            modifier = Modifier.padding(end = 8.dp) // Khoảng cách giữa nội dung và icon tiếp theo
        )

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

@Preview
@Composable
fun AdminInfoScreenPreview(){
    AdminInfoScreen()
}
