package com.example.gotravel.ui.module.admin.UserDetails

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

class AdminUserDetails: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Admin xem thông tin chi tiết user"
    }
    val text: LiveData<String> = _text
}
@Composable
@Preview
fun AdminUserDetailsPreview()
{
    AdminUserDetailsScreen()
}
 @Composable
fun AdminUserDetailsScreen()
 {
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
                     text = "Chi tiết người dùng", // Tiêu đề
                     fontSize = 24.sp,
                     fontWeight = FontWeight.Bold,
                     color = Color.White,  // Màu chữ trắng
                     modifier = Modifier.padding() // Khoảng cách trên
                 )
             }
         }

         // Danh sách các chức năng
         Spacer(modifier = Modifier.height(20.dp))

         UserInfo(
             text = "Tên:",
             startIcon = R.drawable.ic_user_info, // Thay thế bằng icon phù hợp
             content = "Nguyễn Văn A",
             onClick = { /* Hành động */ }
         )

         UserInfo(
             text = "Số điện thoại:",
             startIcon = R.drawable.ic_phone, // Thay thế bằng icon phù hợp
             content = "0123456789",
             onClick = { /* Hành động */ }
         )
         UserInfo(
             text = "Email:",
             startIcon = R.drawable.ic_aim, // Thay thế bằng icon phù hợp
             content = "thoai20052003@gmail.com",
             onClick = { /* Hành động */ }
         )
         UserInfo(
             text = "Password:",
             startIcon = R.drawable.ic_password, // Thay thế bằng icon phù hợp
             content = "*********",
             onClick = { /* Hành động */ }
         )
         UserInfo(
             text = "Địa chỉ:",
             startIcon = R.drawable.ic_aim, // Thay thế bằng icon phù hợp
             content = "Hà Nội",
             onClick = { /* Hành động */ }
         )
         UserInfo(
             text = "Ảnh đại diện:",
             startIcon = R.drawable.ic_user_info, // Thay thế bằng icon phù hợp
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
                 Text(text = "Lưu")
             }

             Spacer(modifier = Modifier.width(16.dp))

             Button(
                 onClick = { /* Từ chối */ },
                 colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                 modifier = Modifier.weight(1f)
             ) {
                 Text(text = "Xóa tài khoản")
             }
         }

     }
 }

@Composable
fun UserInfo(
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