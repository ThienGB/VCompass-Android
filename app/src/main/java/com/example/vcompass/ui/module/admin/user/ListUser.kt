package com.example.vcompass.ui.module.admin.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.vcompass.data.model.User
import com.example.vcompass.ui.components.CustomSearchBar
import com.example.vcompass.ui.components.Loading
import com.example.vcompass.ui.components.NavTitle
import com.example.vcompass.ui.module.admin.main.MainAdminViewModel
import com.example.vcompass.R

@Composable
fun ListUser(
    users: List<User>,
    navController: NavController,
    viewModel: MainAdminViewModel,
    isLoading: Boolean,
    calledBy: String = "user"
) {
    viewModel.setIsShowBottomBar(false)
    val title = if (calledBy == "user") "Danh sách người dùng" else "Danh sách nhà cung cấp"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavTitle(title) { navController.popBackStack() }
        if (isLoading){
            Loading()
        }else{
            CustomSearchBar()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp, horizontal = 5.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start
                ) {
                    users.forEach { user ->
                        if (user.role == calledBy)
                            UserItem(user, navController, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    navController: NavController,
    viewModel: MainAdminViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp)
            .clickable {
                viewModel.setCurrentUser(user)
                navController.navigate("user_infor")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(rememberAsyncImagePainter(user.image),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = user.fullName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = user.email, color = Color.Gray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorResource(id = R.color.primary)),
            modifier = Modifier
                .size(40.dp)
                .padding(start = 8.dp)
        )
    }
    HorizontalDivider(thickness = 0.5.dp, color = colorResource(id = R.color.primary))
}
