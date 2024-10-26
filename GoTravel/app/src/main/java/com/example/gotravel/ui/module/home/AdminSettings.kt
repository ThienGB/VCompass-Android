package com.example.gotravel.ui.module.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.foundation.shape.CircleShape
import com.example.gotravel.R

class AdminSettings : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Settings"
    }
    val text: LiveData<String> = _text
}

@Composable
fun AdminSettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box màu xanh chứa avatar và tên người dùng
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF1A94FF))  // Màu nền xanh
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center // Căn giữa nội dung trong Box
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
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

                // Họ và tên người dùng
                Text(
                    text = "Nguyễn Văn A", // Tên người dùng
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,  // Màu chữ trắng
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Danh sách các chức năng
        Spacer(modifier = Modifier.height(20.dp))
        UserSettingListItem(
            text = "Thông tin người dùng",
            startIcon = R.drawable.ic_user_info, // Thay thế bằng icon phù hợp
            onClick = { /* Hành động */ }
        )

        UserSettingListItem(
            text = "Đổi mật khẩu",
            startIcon = R.drawable.ic_password, // Thay thế bằng icon phù hợp
            onClick = { /* Hành động */ }
        )

        UserSettingListItem(
            text = "Đăng xuất",
            startIcon = R.drawable.ic_log_out, // Thay thế bằng icon phù hợp
            onClick = { /* Hành động */ }
        )
    }
}


@Composable
fun UserSettingListItem(text: String, startIcon: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 15.dp),  // Khoảng cách giữa các mục
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon ở đầu
        Image(
            painter = painterResource(id = startIcon),  // Sử dụng startIcon
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

        // Icon ở cuối
        Image(
            painter = painterResource(id = R.drawable.ic_next), // Icon mặc định ở cuối
            contentDescription = null,
            modifier = Modifier
                .size(28.dp)
                .padding(start = 8.dp)
        )
    }
}




@Preview(showBackground = true)
@Composable
fun AdminSettingsPreview() {
    AdminSettingsScreen()
}