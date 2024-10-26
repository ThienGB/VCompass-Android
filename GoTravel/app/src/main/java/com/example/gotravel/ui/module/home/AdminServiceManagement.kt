package com.example.gotravel.ui.module.home



import com.example.gotravel.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

class AdminServiceManagement : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Admin Fragment"
    }
    val text: LiveData<String> = _text

}

@Composable
fun AdminServiceManagementScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Box màu xanh chứa tiêu đề "Quản lý dịch vụ"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF1A94FF))
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.weight(1f))
                // Tiêu đề "Quản lý dịch vụ"
                Text(
                    text = "Quản lý dịch vụ", // Tiêu đề
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding() // Khoảng cách trên
                )
            }
        }

        // Khoảng cách giữa Box và danh sách dịch vụ
        Spacer(modifier = Modifier.height(30.dp))

        // Box chứa danh sách dịch vụ và nút "Xuất dữ liệu"
        Box(
            modifier = Modifier
                .fillMaxSize()

                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                // Danh sách mẫu 5 dịch vụ
                val services = listOf(
                    Service("Dịch vụ 1", R.drawable.ic_user_info), // Sử dụng icon dịch vụ mẫu
                    Service("Dịch vụ 2", R.drawable.ic_user_info),
                    Service("Dịch vụ 3", R.drawable.ic_user_info),
                    Service("Dịch vụ 4", R.drawable.ic_user_info),
                    Service("Dịch vụ 5", R.drawable.ic_user_info),

                )

                // Hiển thị danh sách dịch vụ
                for (service in services) {
                    ServiceItem(service)
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
fun ServiceItem(service: Service) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White) // Màu nền cho từng item
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon dịch vụ
        Box(
            modifier = Modifier
                .size(40.dp) // Kích thước icon
                .background(Color.Gray, shape = CircleShape), // Icon mẫu
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = service.iconResId),
                contentDescription = service.name,
                modifier = Modifier.size(24.dp) // Kích thước icon trong Box
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Tên dịch vụ
        Text(
            text = service.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f) // Để tên dịch vụ chiếm không gian còn lại
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

data class Service(val name: String, val iconResId: Int)

@Preview(showBackground = true)
@Composable
fun AdminServiceManagementPreview() {
    AdminServiceManagementScreen()
}
