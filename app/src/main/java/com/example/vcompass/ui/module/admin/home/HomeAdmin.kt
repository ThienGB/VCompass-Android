package com.example.vcompass.ui.module.admin.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vcompass.R
import com.example.vcompass.data.model.Accommodation
import com.example.vcompass.data.model.User
import com.example.vcompass.data.model.UserAccount
import com.example.vcompass.ui.components.Loading
import com.example.vcompass.ui.module.admin.main.MainAdminViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeAdmin(
    user: UserAccount,
    accommodations: List<Accommodation> = listOf(),
    users: List<User>,
    isLoading: Boolean = false,
    navController: NavController = NavController(LocalContext.current),
    viewModel: MainAdminViewModel
) {
    val isResfreshing by viewModel.isLoading.collectAsState()
    viewModel.setIsShowBottomBar(true)
    SwipeRefresh(
        state = rememberSwipeRefreshState(isResfreshing),
        onRefresh = {
            viewModel.fetchData()
        }
    ) {
        if (isLoading) {
            Loading()
        } else {
            Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.bg_admin),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(10.dp)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    AdminInforCard(user)
                    Spacer(modifier = Modifier.height(115.dp))
                    Text(
                        text = "Tổng quan",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BookingStats(accommodations, users, navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



@Composable
fun BookingStats(
    accommodations: List<Accommodation> = listOf(),
    users: List<User>,
    navController: NavController
) {
    val partnerCount = users.count { it.role == "partner" }
    val userCount = users.count { it.role == "user" }
    val pendingCount = accommodations.count { it.status != "accept" }
    val successCount = accommodations.count { it.status == "accept" }

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .clickable {
                    navController.navigate("list_user")
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "NGƯỜI DÙNG", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = userCount.toString(), color = colorResource(id = R.color.green),
                    fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Xem chi tiết",color = colorResource(id = R.color.primary),
                    fontStyle = FontStyle.Italic, fontSize = 16.sp, textDecoration = TextDecoration.Underline)
            }
        }
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .clickable { navController.navigate("list_partner") }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "NHÀ CUNG CẤP", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = partnerCount.toString(),color = colorResource(id = R.color.green),
                    fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Xem chi tiết",color = colorResource(id = R.color.primary),
                    fontStyle = FontStyle.Italic, fontSize = 16.sp, textDecoration = TextDecoration.Underline)
            }
        }
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween)
    {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .clickable {
                    navController.navigate("list_accom")
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "CHỖ Ở", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = successCount.toString(), color = colorResource(id = R.color.green),
                    fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Xem chi tiết",color = colorResource(id = R.color.primary),
                    fontStyle = FontStyle.Italic, fontSize = 16.sp, textDecoration = TextDecoration.Underline)
            }
        }
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .clickable { navController.navigate("list_accom_register") }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "ĐƠN CHƯA DUYỆT", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = pendingCount.toString(),color = colorResource(id = R.color.green),
                    fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Xem chi tiết",color = colorResource(id = R.color.primary),
                    fontStyle = FontStyle.Italic, fontSize = 16.sp, textDecoration = TextDecoration.Underline)
            }
        }
    }
}

@Composable
fun AdminInforCard(
    user: UserAccount
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer(alpha = 0.9f)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Transparent, RoundedCornerShape(99.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = "Xin chào admin", color = Color.Gray, fontSize = 14.sp)
                    Text(text = "Admin", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}