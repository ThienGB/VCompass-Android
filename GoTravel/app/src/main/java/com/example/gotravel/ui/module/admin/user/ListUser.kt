package com.example.gotravel.ui.module.admin.user

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.gotravel.data.model.User
import com.example.gotravel.ui.components.CustomSearchBar
import com.example.gotravel.ui.components.NavTitle

@Composable
fun ListUser(
    users: List<User>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavTitle("Danh sách người dùng") { navController.popBackStack() }
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
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
                UserItem()
            }
        }
    }
}

@Composable
fun UserItem(
    user: User = User("", "Cong Thien", "thienlove161203@gmail.com", "user")
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(rememberAsyncImagePainter("https://scontent.fsgn8-3.fna.fbcdn.net/v/t39.30808-6/464155622_1188059512260875_297674526356158310_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeHxKZsoRqZfIT9epYsvGl55ad4eogWvx0Bp3h6iBa_HQKJ_l10yFyfLYqgoFhRnnssbOi42kaZiAurWnXO3DP0I&_nc_ohc=-r4h1mmadcYQ7kNvgFlrD7B&_nc_zt=23&_nc_ht=scontent.fsgn8-3.fna&_nc_gid=ARbsAeXYjviHc5FEmRJcUv_&oh=00_AYCP39wbGG_lHowyPkbw-ZOyAvQ1p4fBX1oDHs9e1X7ZBw&oe=6746F57A"),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "Hoàng Công Thiện", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "thienlove161203@gmail.com", color = Color.Gray, fontSize = 14.sp)
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
