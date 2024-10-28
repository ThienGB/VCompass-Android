package com.example.gotravel.ui.module.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

class AdminSendNotification : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Send Notification Admin"
    }
    val text: LiveData<String> = _text
}

@Composable
fun AdminNotificationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Thay đổi màu nền toàn bộ màn hình
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box màu xanh chứa avatar và tên người dùng
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF1A94FF))  // Màu nền xanh
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween, // Căn dưới cho nội dung trong Column
                horizontalAlignment = Alignment.Start // Căn trái cho text
            ) {
                Spacer(modifier = Modifier.weight(1f))
                // Tiêu đề "Gửi thông báo"
                Text(
                    text = "Gửi thông báo", // Tiêu đề
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,  // Màu chữ trắng
                    modifier = Modifier.padding() // Khoảng cách trên
                )
            }
        }

        // Khoảng cách giữa Box và các TextField
        Spacer(modifier = Modifier.height(30.dp))

        // Box màu trắng chứa thông tin người nhận, tiêu đề và nội dung thông báo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start // Căn trái cho các TextField
            ) {
                // Nhập người nhận thông báo
                TextField(
                    value = "",
                    onValueChange = { /* Cập nhật người nhận thông báo */ },
                    label = { Text("Người nhận thông báo") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Khoảng cách giữa các TextField
                Spacer(modifier = Modifier.height(20.dp))

                // Nhập tiêu đề thông báo
                TextField(
                    value = "",
                    onValueChange = { /* Cập nhật tiêu đề thông báo */ },
                    label = { Text("Tiêu đề thông báo") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Khoảng cách giữa các TextField
                Spacer(modifier = Modifier.height(20.dp))

                // Nhập nội dung thông báo
                TextField(
                    value = "",
                    onValueChange = { /* Cập nhật nội dung thông báo */ },
                    label = { Text("Nội dung thông báo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp), // Chiều cao cho nội dung dài
                    maxLines = 5 // Số dòng tối đa
                )
            }
        }

        // Khoảng cách giữa nội dung và nút gửi
        Spacer(modifier = Modifier.height(20.dp))

        // Nút gửi thông báo
        Button(
            onClick = { /* Gửi thông báo */ },
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Gửi")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminNotificationPreview() {
    AdminNotificationScreen()
}
