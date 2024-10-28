package com.example.gotravel.ui.module.home

import com.example.gotravel.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue // Import getValue
import androidx.compose.runtime.setValue // Import setValue
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

class AdminServiceApproval : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Send Notification Admin"
    }
    val text: LiveData<String> = _text
}

@Preview
@Composable
fun AdminServiceApprovalPreview() {
    AdminServiceApprovalScreen()
}

@Composable
fun AdminServiceApprovalScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        // Box màu xanh chứa tiêu đề "Duyệt dịch vụ"
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
                // Tiêu đề "Duyệt dịch vụ"
                Text(
                    text = "Duyệt dịch vụ", // Tiêu đề
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding() // Khoảng cách trên
                )
            }
        }

        // Khoảng cách giữa Box và danh sách dịch vụ
        Spacer(modifier = Modifier.height(30.dp))

        // Box chứa danh sách dịch vụ và hai nút "Duyệt" và "Từ chối"
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
                // Danh sách mẫu 5 dịch vụ cần duyệt
                val services = listOf(
                    ServiceApproval("Dịch vụ 1", R.drawable.ic_user_info),
                    ServiceApproval("Dịch vụ 2", R.drawable.ic_user_info),
                    ServiceApproval("Dịch vụ 3", R.drawable.ic_user_info),
                    ServiceApproval("Dịch vụ 4", R.drawable.ic_user_info),
                    ServiceApproval("Dịch vụ 5", R.drawable.ic_user_info)
                )

                // Hiển thị danh sách dịch vụ với checkbox
                for (service in services) {
                    ServiceApprovalItem(service)
                    Spacer(modifier = Modifier.height(5.dp)) // Khoảng cách giữa các mục
                }

                // Spacer để tạo khoảng cách giữa danh sách và các nút
                Spacer(modifier = Modifier.weight(1f))

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
                        Text(text = "Duyệt")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { /* Từ chối */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Từ chối")
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceApprovalItem(service: ServiceApproval) {
    var isChecked by remember { mutableStateOf(false) }

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

        // Checkbox ở cuối hàng
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            modifier = Modifier.size(24.dp)
        )
    }
}

data class ServiceApproval(val name: String, val iconResId: Int)
