package com.example.vcompass.ui.module.admin.user

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vcompass.R
import com.example.vcompass.data.model.User

@Preview(showSystemUi = true)
@Composable
fun UserInfor(
    user: User = User(),
    navController: NavController = NavController(LocalContext.current),
    onBanClick: () -> Unit = {},
    onUnBanClick: () -> Unit = {}
){
    val context = LocalContext.current
    Column (modifier = Modifier
        .verticalScroll(rememberScrollState())
        .background(Color.White)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(contentAlignment = Alignment.TopCenter) {
            Image(
                painter = painterResource(id = R.drawable.bg_profile),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
            Box(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .size(100.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .padding(4.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.image),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Box(modifier = Modifier.fillMaxWidth()){
                IconButton(
                    onClick = {navController.popBackStack()},
                    modifier = Modifier
                        .padding(16.dp)
                        .size(30.dp)
                        .background(color = Color.Black.copy(alpha = 0.15f), shape = CircleShape) // Nền tròn mờ
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        modifier = Modifier.size(35.dp),
                        tint = Color.White
                    )
                }
            }
            Text(text = user.fullName, color = Color.White,
                fontWeight = Bold,
                fontSize = 20.sp,  modifier = Modifier
                    .padding(top = 180.dp))
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(top = 230.dp, start = 30.dp, end = 30.dp, bottom = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InforProfile("Họ và tên", user.fullName)
                    InforProfile("Email", user.email)
                    InforProfile("Số điện thoại", user.phone)
                    InforProfile("Vai trò", user.role)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        if (user.status != "banned"){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 16.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(99.dp))
                    .background(Color(0xFFE71100))
                    .clickable {
                        onBanClick()
                        navController.popBackStack()
                        Toast.makeText(context, "Cấm người dùng thành công", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Image(painter = painterResource(id = R.drawable.ic_x_circle),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(25.dp),
                    colorFilter = ColorFilter.tint(Color.White))
                Text(text = "Cấm người dùng", color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp, end = 20.dp))
            }
        }else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 16.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(99.dp))
                    .background(Color(0xFF03D307))
                    .clickable {
                        onUnBanClick()
                        navController.popBackStack()
                        Toast.makeText(context, "Hủy cấm người dùng thành công", Toast.LENGTH_SHORT).show()
                    }
            ) {
                Image(painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(25.dp),
                    colorFilter = ColorFilter.tint(Color.White))
                Text(text = "Hủy cấm người dùng", color = Color.White,
                    fontFamily = FontFamily(Font(R.font.proxima_nova_regular)),
                    fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp, end = 20.dp))
            }
        }

    }
}
@Composable
fun InforProfile(
    field: String,
    value: String
){
    Row(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(text = field, color = Color.Gray, fontSize = 15.sp)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value, fontSize = 15.sp)
    }
}