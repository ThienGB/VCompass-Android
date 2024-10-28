package com.example.gotravel.ui.module.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminChangePassword: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Change Password admin"
    }
    val text: LiveData<String> = _text
}
@Preview
@Composable
fun AdminChangePasswordPreview()
{
    AdminChangePasswordScreen()
}
@Composable
fun AdminChangePasswordScreen()
{
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
                    text = "Đổi mật khẩu", // Tiêu đề
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
                    label = { Text("Nhập mật khẩu") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Khoảng cách giữa các TextField
                Spacer(modifier = Modifier.height(20.dp))

                // Nhập tiêu đề thông báo
                TextField(
                    value = "",
                    onValueChange = { /* Cập nhật tiêu đề thông báo */ },
                    label = { Text("Nhập mật khẩu mới") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Khoảng cách giữa các TextField
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = "",
                    onValueChange = { /* Cập nhật tiêu đề thông báo */ },
                    label = { Text("Xác nhận mật khẩu mới") },
                    modifier = Modifier.fillMaxWidth()
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
            Text(text = "Đổi mật khẩu")
        }
    }
}