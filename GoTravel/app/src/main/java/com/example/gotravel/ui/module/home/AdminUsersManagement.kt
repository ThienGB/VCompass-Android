package com.example.gotravel.ui.module.home



import com.example.gotravel.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdminUsersManagement : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "Settings"
    }
    val text: LiveData<String> = _text
}


@Composable
fun AdminUserManagementScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Màu nền trắng cho toàn bộ màn hình
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box màu xanh chứa tiêu đề "Quản lý người dùng"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF1A94FF))  // Màu nền xanh
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.weight(1f))
                // Tiêu đề "Quản lý người dùng"
                Text(
                    text = "Quản lý người dùng", // Tiêu đề
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding() // Khoảng cách trên
                )
            }
        }

        // Khoảng cách giữa Box và danh sách người dùng
        Spacer(modifier = Modifier.height(30.dp))

        // Box chứa danh sách người dùng và nút "Xuất dữ liệu"
        Box(
            modifier = Modifier
                .fillMaxSize()

                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween, // Sắp xếp theo chiều dọc
                horizontalAlignment = Alignment.Start
            ) {
                // Danh sách mẫu 5 người dùng
                val users = listOf(
                    User("Nguyễn Văn A", R.drawable.ic_user_info), // Sử dụng avatar mẫu
                    User("Trần Thị B", R.drawable.ic_user_info),
                    User("Lê Văn C", R.drawable.ic_user_info),
                    User("Phạm Thị D", R.drawable.ic_user_info),
                    User("Hoàng Văn E", R.drawable.ic_user_info)

                )

                // Hiển thị danh sách người dùng
                for (user in users) {
                    UserItem(user)
                    Spacer(modifier = Modifier.height(5.dp)) // Khoảng cách giữa các mục
                }

                // Spacer để tạo khoảng cách giữa danh sách và nút "Xuất dữ liệu"
                Spacer(modifier = Modifier.weight(1f))

                // Nút "Xuất dữ liệu"
                Button(
                    onClick = { /* Xuất dữ liệu */ },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(text = "Xuất dữ liệu")
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White) // Màu nền cho từng item
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar người dùng
        Box(
            modifier = Modifier
                .size(40.dp) // Kích thước avatar
                .background(Color.Gray, shape = CircleShape), // Avatar mẫu
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "A", // Chữ cái đại diện cho người dùng
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Tên người dùng
        Text(
            text = user.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f) // Để tên người dùng chiếm không gian còn lại
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

data class User(val name: String, val avatarResId: Int)

@Preview(showBackground = true)
@Composable
fun AdminUserManagementPreview() {
    AdminUserManagementScreen()
}
