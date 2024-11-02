package com.example.gotravel.ui.module.admin.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gotravel.R

class AdminHomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Admin Fragment"
    }
    val text: LiveData<String> = _text

}

@Composable
fun AdminHomeScreen() {
    AdminHome()
}

@Preview
@Composable
fun AdminHome() {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        // Box màu xanh trên cùng
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF1A94FF))
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hello Admin",
                    color = Color.White,
                    fontSize = 25.sp
                )

                // Hình tròn tượng trưng cho avatar
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, CircleShape)
                )
            }
        }

        // Box màu trắng bên dưới, chiếm phần còn lại của màn hình
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                GeneralIconGrid()
                Spacer(modifier = Modifier.height(16.dp))


                UserIconGrid()

                Spacer(modifier = Modifier.height(16.dp))

                PartnerIconGrid()
            }
        }
    }
}

@Composable
fun UserIconGrid() {
    val userIcons = listOf(
        Pair(R.drawable.ic_user_detail, "Quản lý người dùng"),
    )

    Text(
        text = "Người Dùng",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 16.dp) // Tăng padding dưới
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(bottom = 16.dp) // Thêm padding dưới cho Grid
    ) {
        items(userIcons) { icon ->
            IconWithText(icon.first, icon.second)
        }
    }
}

@Composable
fun PartnerIconGrid() {
    val partnerIcons = listOf(
        Pair(R.drawable.ic_service_management, "Quản lý dịch vụ"),
        Pair(R.drawable.ic_approval, "Duyệt dịch vụ")
    )

    Text(
        text = "Partner",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 16.dp) // Tăng padding trên và dưới
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(bottom = 16.dp) // Thêm padding dưới cho Grid
    ) {
        items(partnerIcons) { icon ->
            IconWithText(icon.first, icon.second)
        }
    }
}

@Composable
fun GeneralIconGrid() {
    val generalIcons = listOf(
        Pair(R.drawable.ic_notification, "Gửi thông báo"),
        Pair(R.drawable.ic_line_chart, "Thống kê"),
    )

    Text(
        text = "Chức năng chung",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier.padding(bottom = 16.dp) // Tăng padding trên và dưới
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(bottom = 16.dp) // Thêm padding dưới cho Grid
    ) {
        items(generalIcons) { icon ->
            IconWithText(icon.first, icon.second)
        }
    }
}

@Composable
fun IconWithText(icon: Int, label: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(90.dp) // Đảm bảo độ rộng để giữ cho text không bị rộng quá
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF9A73FF), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    modifier = Modifier.size(40.dp)
                )
            }

            // Text với padding top 8dp
            Text(
                text = label,
                fontSize = 15.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.CenterHorizontally), // Căn giữa text
                textAlign = TextAlign.Center, // Căn giữa text
                maxLines = 2, // Giới hạn số dòng hiển thị
                overflow = TextOverflow.Visible // Đảm bảo không dùng "..." khi tràn
            )
        }
    }
}
